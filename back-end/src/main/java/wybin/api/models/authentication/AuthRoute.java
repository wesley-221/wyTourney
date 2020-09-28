package wybin.api.models.authentication;

public class AuthRoute {
    private String url;
    private boolean requiresAdmin;
    private RequestType requestType;

    // TODO: Implement new Role enum permission to

    public AuthRoute(String url, boolean requiresAdmin, RequestType requestType) {
        this.url = url;
        this.requiresAdmin = requiresAdmin;
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRequiresAdmin() {
        return requiresAdmin;
    }

    public void setRequiresAdmin(boolean requiresAdmin) {
        this.requiresAdmin = requiresAdmin;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
