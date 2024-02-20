package taiga.api;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import settings.Settings;
import taiga.model.auth.RefreshResponse;
import taiga.model.auth.Tokens;
import taiga.util.AuthTokenSingleton;
import taiga.util.HTTPClientSingleton;
import taiga.util.TokenStore;

public abstract class APIWrapperBase {

    private static String apiBaseURL;
    private final String apiEndpoint;

    public APIWrapperBase(String endpoint) {
        apiBaseURL = Settings.get().getAppModel().getApiURL();
        this.apiEndpoint = endpoint;
    }

    public static void setAPIBaseURL(String url) {
        apiBaseURL = url;
    }

    /**
     * Construct an APIResponse object from the received HttpResponse.
     *
     * @param response received HttpResponse object
     * @param contentType Class object representing type to deserialize response to
     * @param <T> type to deserialize response to
     * @return APIResponse object with HTTP status code and deserialized object
     */
    protected <T> APIResponse<T> createResponse(
            HttpResponse<String> response, Class<T> contentType) {
        // Check if a response was received
        if (response != null) {
            // Check if request was successful
            if (response.statusCode() == 200) {
                // If so, deserialize object, wrap it in a APIResponse, and return it
                String json = response.body();
                Gson gson = new Gson();
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
     * @param query query string to be appended to the base API endpoint configured.
     * @param responseType class of expected response object.
     * @return future with result object.
     */
    protected <T> CompletableFuture<APIResponse<T>> queryAsync(
            String query, Class<T> responseType) {
        return queryAsync(query, responseType, true, false);
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
     * @param query query string to be appended to the base API endpoint configured.
     * @param responseType class of expected response object.
     * @param retry whether to try to refresh the auth token and try again if the first
     *              request try fails
     * @param enable_pagination whether to enable pagination or not. This should be false in most
     *                          scenarios.
     * @param <T> type of expected response object.
     * @return future with result object.
     */
    protected <T> CompletableFuture<APIResponse<T>> queryAsync(
            String query, Class<T> responseType, boolean retry, boolean enable_pagination) {
        try {
            HttpRequest.Builder request =
                    HttpRequest.newBuilder()
                            .uri(new URI(apiBaseURL + apiEndpoint + query))
                            .header("Content-Type", "application/json");

            if (!enable_pagination) {
                request.header("x-disable-pagination", "true");
            }

            String authToken = getAuthToken();

            if (authToken != null) {
                request.header("Authorization", "Bearer " + authToken);
            }

            return HTTPClientSingleton.getInstance()
                    .sendAsync(request.GET().build(), HttpResponse.BodyHandlers.ofString())
                    .exceptionally(error -> {
                        error.printStackTrace();
                        return null;
                    })
                    .thenApply(response -> {
                        AtomicReference<APIResponse<T>> apiResponse =
                                new AtomicReference<>(createResponse(response, responseType));

                        if (retry && apiResponse.get().getStatus() == 401) {
                            refreshAuthToken(query, responseType, apiResponse, retry, enable_pagination);
                        }
                        return apiResponse.get();
                    });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Refreshes the authentication token and retries the query if needed.
     *
     * @param <T> The type of expected response object.
     * @param query The query string to be appended to the base API endpoint configured.
     * @param responseType The class of expected response object.
     * @param apiResponse The reference to the APIResponse object.
     * @param retry Indicates whether to retry the query if authentication token refresh is needed.
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
     * @param path query string to be appended to the base API endpoint configured.
     * @param body Object containing the data that will be sent as a JSON body
     * @param responseType class of expected response object.
     * @param <T> type of expected response object.
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
     * @param path query string to be appended to the base API endpoint configured.
     * @param body Object containing the data that will be sent as a JSON body
     * @param responseType class of expected response object.
     * @param useAuth whether to use our auth tokens when submitting the request
     * @param <T> type of expected response object.
     * @return future with result object.
     */
    protected <T, U> CompletableFuture<APIResponse<T>> postAsync(
            String path, U body, Class<T> responseType, boolean useAuth) {
        try {
            Gson gson = new Gson();
            // Construct HTTP request
            HttpRequest.Builder request =
                    HttpRequest.newBuilder()
                            .uri(new URI(apiBaseURL + apiEndpoint + path))
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
}
