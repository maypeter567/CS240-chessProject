package request;

import models.AuthTokenMod;

public class ListGamesRequest {
    private AuthTokenMod authToken;
    
    /**
     * this class only keeps auth Token
     */
    public ListGamesRequest() {}
    
    public AuthTokenMod getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(AuthTokenMod authToken) {
        this.authToken = authToken;
    }
}
