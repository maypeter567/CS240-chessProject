package models;

public class AuthTokenMod {
    private final String authToken;
    private final String username;
    
    /**
     * this object is used to keep the player recognized by the server and access the database.
     *
     * @param authToken the value from the server for its authentication.
     * @param username the name attached with the authentication.
     */
    public AuthTokenMod(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }
    
    public String getAuthToken() {
        return authToken;
    }
    
    public String getUsername() {
        return username;
    }
}
