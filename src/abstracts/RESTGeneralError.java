package abstracts;

public enum RESTGeneralError {

    NOT_IDENTIFY(-1, "Not Identify"),
    BAD_REQUEST(400, "Bad request"),
    INVALID_CREDENTIALS(401, "Invalid Credentials"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    SERVER_ERROR(500, "Server Error"),
    DATASTORE_ERROR(500, "Datastore Error");

    private final int code;
    private final String description;

    private RESTGeneralError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String description() {
        return description;
    }
    public int code() {
        return code;
    }
}
