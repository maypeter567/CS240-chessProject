package result;

public class CreateGameResult {
    private String gameID;
    private String message;
    
    /**
     * gameID and message are the expected return values for CreateGame
     */
    public CreateGameResult() {}
    
    public String getGameID() {
        return gameID;
    }
    
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
