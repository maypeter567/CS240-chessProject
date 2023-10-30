package result;

import models.AuthTokenMod;

public class LoginResult {
    private String message;
    private String authToken;
    private String username;
    
    /**
     * message, authToken, and username are recorded
     */
    public LoginResult() {}
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
