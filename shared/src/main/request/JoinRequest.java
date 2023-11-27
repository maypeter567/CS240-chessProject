package request;

import models.AuthTokenMod;

public class JoinRequest {
    private AuthTokenMod authToken;
    private String playerColor;
    private int gameID;
    
    /**
     * Join request class keeps track of authToken, playerColor, and gameID for requests
     */
    public JoinRequest() {}
    
    public AuthTokenMod getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(AuthTokenMod authToken) {
        this.authToken = authToken;
    }
    
    public String getPlayerColor() {
        return playerColor;
    }
    
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
    
    public int getGameID() {
        return gameID;
    }
    
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
