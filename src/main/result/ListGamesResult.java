package result;

public class ListGamesResult {
    private String games;
    private String message;
    
    /**
     * games and message are recorded
     */
    public ListGamesResult() {}
    
    public String getGames() {
        return games;
    }
    
    public void setGames(String games) {
        this.games = games;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
