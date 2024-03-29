package bostonhttp.api;

/**
 * A response from the Taiga API containing an HTTP status code and the response body object.
 *
 * @param <T> type of response
 */
public class APIResponse<T> {
    private final int status;
    private final T content;

    public APIResponse(int status, T content) {
        this.status = status;
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public T getContent() {
        return content;
    }
}
