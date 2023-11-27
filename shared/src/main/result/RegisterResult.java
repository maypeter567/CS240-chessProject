package result;

public class RegisterResult {
    private String username;
    private String authToken;
    private String message;
    
    /**
     * username, authToken, and message are recorded
     */
    public RegisterResult() {}
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
