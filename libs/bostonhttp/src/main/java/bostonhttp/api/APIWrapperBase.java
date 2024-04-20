package bostonhttp.api;

import bostonhttp.models.AuthToken;
import bostonhttp.models.RefreshResponse;
import bostonhttp.models.Tokens;
import bostonhttp.util.AuthTokenSingleton;
import bostonhttp.util.HTTPClientSingleton;
import bostonhttp.util.LocalDateAdapter;
import bostonhttp.util.TokenStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class APIWrapperBase {

    private APIWrapperBehaviors behaviors;
    private String apiBaseURL;

    private final String apiEndpoint;

    private final Map<String, Semaphore> semaphores = new ConcurrentHashMap<>();

    private final static String DEFAULT_BASE_API_URL = "https://api.taiga.io/api/v1/";
    private static final int MAX_CONCURRENT_REQUESTS_NUMBER = 100;
    private static final int MAX_RETRY_ATTEMPTS = 5;
    private static final long INITIAL_RETRY_DELAY_MS = 1000;

    public APIWrapperBase(String endpoint) {
        this.apiEndpoint = endpoint;
        this.behaviors = new APIWrapperBehaviors();
    }

    public APIWrapperBase(String endpoint, APIWrapperBehaviors behaviors) {
        this(endpoint);
        this.behaviors = behaviors;
    }

    /**
     * Manually override the api base url. Normally only used in testing.
     *
     * @param url the url of the taiga api
     */
    public void setAPIBaseURL(String url) {
        apiBaseURL = url;
    }

    /**
     * Helper function to get the api base url. Defaults to what the settings has, but if the baseurl
     * has been set manually (through setAPIBaseURL()) then that value will override.
     *
     * @return the taiga api base url
     */
    private String getAPIBaseURL() {
        if (this.apiBaseURL != null) {
            return this.apiBaseURL;
        }
        if (this.behaviors != null && this.behaviors.getBaseApiUrlResolver() != null) {
            return this.behaviors.getBaseApiUrlResolver().resolve();
        }
        return DEFAULT_BASE_API_URL;
    }

    /**
     * Construct an apiwrapper.APIResponse object from the received HttpResponse.
     *
     * @param response    received HttpResponse object
     * @param contentType Class object representing type to deserialize response to
     * @param <T>         type to deserialize response to
     * @return apiwrapper.APIResponse object with HTTP status code and deserialized object
     */
    protected <T> APIResponse<T> createResponse(
            HttpResponse<String> response, Class<T> contentType) {
        // Check if a response was received
        if (response != null) {
            // Check if request was successful
            if (response.statusCode() == 200) {
                // If so, deserialize object, wrap it in a apiwrapper.APIResponse, and return it
                String json = response.body();
                Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();
                try {
                    T r = gson.fromJson(json, contentType);
                    return new APIResponse<>(200, r);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new APIResponse<>(500, null);
                }
            } else {
                // If an error code was received, return it
                return new APIResponse<>(response.statusCode(), null);
            }
        } else {
            // If no response was received, just return a 500
            return new APIResponse<>(500, null);
        }
    }

    /**
     * Send an asynchronous GET request with the specified query to the configured API endpoint.
     * Unlike the synchronous version of this method, this method will handle converting the raw
     * response to an {@link APIResponse} of the given type.
     *
     * <p>Returns a {@link CompletableFuture} holding the {@link APIResponse} response object.
     *
     * <p>Example usage:
     *
     * <pre>
     *     queryAsync("?project=12345", Membership[].class).thenAccept(response -> {
     *         System.out.println(response.getStatus());
     *         System.out.println(response.getContent()[0].getFullName());
     *     });
     * </pre>
     *
     * @param query        query string to be appended to the base API endpoint configured.
     * @param responseType class of expected response object.
     * @return future with result object.
     */
    protected <T> CompletableFuture<APIResponse<T>> queryAsync(
            String query, Class<T> responseType) {
        return queryAsync(query, responseType, true, false);
    }

    protected <T> CompletableFuture<APIResponse<T>> queryAsync(
        String query, Class<T> responseType, AuthToken token) {
        return queryAsync(query, responseType, token, true, false);
    }

    protected <T> CompletableFuture<APIResponse<T>> queryAsync(
        String query, Class<T> responseType, boolean retry, boolean enable_pagination) {
        return queryAsync(query, responseType, null, retry, enable_pagination);
    }

    /**
     * Send an asynchronous GET request with the specified query to the configured API endpoint.
     * Unlike the synchronous version of this method, this method will handle converting the raw
     * response to an {@link APIResponse} of the given type.
     *
     * <p>Returns a {@link CompletableFuture} holding the {@link APIResponse} response object.
     *
     * <p>Example usage:
     *
     * <pre>
     *     queryAsync("?project=12345", Membership[].class).thenAccept(response -> {
     *         System.out.println(response.getStatus());
     *         System.out.println(response.getContent()[0].getFullName());
     *     });
     * </pre>
     *
     * @param query             query string to be appended to the base API endpoint configured.
     * @param responseType      class of expected response object.
     * @param retry             whether to try to refresh the auth token and try again if the first
     *                          request try fails
     * @param enable_pagination whether to enable pagination or not. This should be false in most
     *                          scenarios.
     * @param <T>               type of expected response object.
     * @return future with result object.
     */
    protected <T> CompletableFuture<APIResponse<T>> queryAsync(
        String query, Class<T> responseType, AuthToken token, boolean retry, boolean enable_pagination) {
        try {
            HttpRequest.Builder request =
                    HttpRequest.newBuilder()
                            .uri(new URI(getAPIBaseURL() + apiEndpoint + query))
                            .header("Content-Type", "application/json");

            if (!enable_pagination) {
                request.header("x-disable-pagination", "true");
            }


            if (token != null) {
                String authToken = token.getAuth();
                request.header("Authorization", "Bearer " + authToken);
            }
            else if(behaviors.getAuthToken() != null){
                request.header("Authorization", "Bearer " + behaviors.getAuthToken().getAuth());
            }

            Semaphore semaphore = semaphores.computeIfAbsent(getRequestKey(request.build()), k -> new Semaphore(MAX_CONCURRENT_REQUESTS_NUMBER));

            // Acquire a permit from the semaphore
            semaphore.acquireUninterruptibly();

            return CompletableFuture.supplyAsync(() -> {
                        try {
                            return HTTPClientSingleton.getInstance()
                                    .sendAsync(request.GET().build(), HttpResponse.BodyHandlers.ofString())
                                    .thenApply(response -> {
                                        AtomicReference<APIResponse<T>> apiResponse =
                                                new AtomicReference<>(createResponse(response, responseType));

                                          //temporarily disabled while microservices are moved
//                                        if (retry && apiResponse.get().getStatus() == 401) {
//                                            refreshAuthToken(query, responseType, apiResponse, retry, enable_pagination);
//                                        } else
                                            if (apiResponse.get().getStatus() == 429) {
                                            // Retry the request after a delay if encountering too many concurrent streams error
                                            retryWithBackoff(query, responseType, apiResponse, retry, enable_pagination, 1);
                                        }
                                        return apiResponse.get();
                                    })
                                    .join();
                        } finally {
                            // Always release the semaphore, even if an exception occurs
                            semaphore.release();
                        }
                    })
                    .exceptionally(error -> {
                        error.printStackTrace();
                        return null;
                    });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Generate a unique key for the given HTTP request.
     *
     * @param request the HTTP request
     * @return a unique key for the request
     */
    private String getRequestKey(HttpRequest request) {
        return request.uri().toString();
    }

    /**
     * Refreshes the authentication token and retries the query if needed.
     *
     * @param <T>               The type of expected response object.
     * @param query             The query string to be appended to the base API endpoint configured.
     * @param responseType      The class of expected response object.
     * @param apiResponse       The reference to the apiwrapper.APIResponse object.
     * @param retry             Indicates whether to retry the query if authentication token refresh is needed.
     * @param enable_pagination Indicates whether pagination should be enabled.
     */
    private <T> void refreshAuthToken(String query, Class<T> responseType,
                                      AtomicReference<APIResponse<T>> apiResponse,
                                      boolean retry, boolean enable_pagination) {
        AuthAPI auth = new AuthAPI();
        Tokens tokens = TokenStore.retrieveTokens();
        String refresh = (tokens == null) ? null : tokens.getRefresh();

        if (refresh != null) {
            System.out.println("attempting to refresh auth token.");

            AtomicReference<APIResponse<RefreshResponse>> refreshAPIResponse = new AtomicReference<>();

            CompletableFuture<Void> completableFutureRefresh = auth.refresh(refresh, refreshAPIResponse::set);

            completableFutureRefresh.join();

            RefreshResponse refreshResponse = refreshAPIResponse.get().getContent();
            tokens = new Tokens();

            if (refreshAPIResponse.get().getStatus() == 200) {
                tokens.setAuth(refreshResponse.getAuthToken());
                tokens.setRefresh(refreshResponse.getRefresh());
            } else {
                tokens.setAuth(null);
                tokens.setRefresh(null);
            }

            TokenStore.saveTokens(tokens);
            AuthTokenSingleton.getInstance().setTokens(tokens);

            CompletableFuture<APIResponse<T>> completableFutureQuery =
                    queryAsync(query, responseType, false, enable_pagination);

            completableFutureQuery.thenAccept(apiResponse::set);
            completableFutureQuery.join();
        }
    }

    private <T> void retryWithBackoff(String query, Class<T> responseType,
                                      AtomicReference<APIResponse<T>> apiResponse,
                                      boolean retry, boolean enable_pagination,
                                      int retryAttempt) {
        if (retryAttempt > MAX_RETRY_ATTEMPTS) {
            // Max retry attempts reached, log an error and return
            System.out.println("Max retry attempts reached. Unable to process request.");
            return;
        }

        // Exponential backoff
        long delayMs = INITIAL_RETRY_DELAY_MS * (1L << (retryAttempt - 1));

        // Retry the request after the calculated delay
        CompletableFuture.delayedExecutor(delayMs, TimeUnit.MILLISECONDS).execute(() -> {
            CompletableFuture<APIResponse<T>> completableFutureQuery =
                    queryAsync(query, responseType, false, enable_pagination);

            completableFutureQuery.thenAccept(result -> {
                if (result != null) {
                    // Request succeeded, set the API response and return
                    apiResponse.set(result);
                } else {
                    // Request failed, retry with backoff
                    retryWithBackoff(query, responseType, apiResponse, retry, enable_pagination, retryAttempt + 1);
                }
            });
        });
    }

    /**
     * Send an asynchronous POST request with the specified query to the configured API endpoint.
     * Unlike the synchronous version of this method, this method will handle converting the raw
     * response to an {@link APIResponse} of the given type.
     *
     * <p>Returns a {@link CompletableFuture} holding the {@link APIResponse} response object.
     *
     * <p>Example usage:
     *
     * <pre>
     *     postAsync("/refresh", RefreshResponse.class).thenAccept(response -> {
     *         System.out.println(response.getStatus());
     *         System.out.println(response.getContent().getRefresh());
     *     });
     * </pre>
     *
     * @param path         query string to be appended to the base API endpoint configured.
     * @param body         Object containing the data that will be sent as a JSON body
     * @param responseType class of expected response object.
     * @param <T>          type of expected response object.
     * @return future with result object.
     */
    protected <T, U> CompletableFuture<APIResponse<T>> postAsync(
            String path, U body, Class<T> responseType) {
        return postAsync(path, body, responseType, true);
    }

    /**
     * Send an asynchronous POST request with the specified query to the configured API endpoint.
     * Unlike the synchronous version of this method, this method will handle converting the raw
     * response to an {@link APIResponse} of the given type.
     *
     * <p>Returns a {@link CompletableFuture} holding the {@link APIResponse} response object.
     *
     * <p>Example usage:
     *
     * <pre>
     *     postAsync("/refresh", RefreshResponse.class).thenAccept(response -> {
     *         System.out.println(response.getStatus());
     *         System.out.println(response.getContent().getRefresh());
     *     });
     * </pre>
     *
     * @param path         query string to be appended to the base API endpoint configured.
     * @param body         Object containing the data that will be sent as a JSON body
     * @param responseType class of expected response object.
     * @param useAuth      whether to use our auth tokens when submitting the request
     * @param <T>          type of expected response object.
     * @return future with result object.
     */
    protected <T, U> CompletableFuture<APIResponse<T>> postAsync(
            String path, U body, Class<T> responseType, boolean useAuth) {
        try {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();
            // Construct HTTP request
            HttpRequest.Builder request =
                    HttpRequest.newBuilder()
                            .uri(new URI(getAPIBaseURL() + apiEndpoint + path))
                            .header("Content-Type", "application/json")
                            .header("x-disable-pagination", "true")
                            .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)));

            // Add token to header if we have one
            if (useAuth) {
                if (getAuthToken() != null) {
                    request.header("Authorization", "Bearer " + getAuthToken());
                }
            }

            // Make request
            return HTTPClientSingleton.getInstance()
                    .sendAsync(request.build(), HttpResponse.BodyHandlers.ofString())
                    .exceptionally(error -> null)
                    .thenApply(response -> createResponse(response, responseType));
        } catch (URISyntaxException | JsonIOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // public so it can be mocked
    public String getAuthToken() {
        AuthTokenSingleton authTokenSingleton = AuthTokenSingleton.getInstance();
        Tokens tokens = authTokenSingleton.getTokens();
        return (tokens == null) ? null : tokens.getAuth();
    }

    public void setBehaviors(APIWrapperBehaviors behaviors) {
        this.behaviors = behaviors;
    }
}
