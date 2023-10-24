package result;

import models.GameMod;

import java.util.Vector;

public class ListGamesResult {
    private Vector<GameMod> games;
    private String message;
    
    /**
     * games and message are recorded
     */
    public ListGamesResult() {}
    
    public Vector<GameMod> getGames() {
        return games;
    }
    
    public void setGames(Vector<GameMod> games) {
        this.games = games;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
