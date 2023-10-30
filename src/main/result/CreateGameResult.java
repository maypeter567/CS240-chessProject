package result;

public class CreateGameResult {
    private Integer gameID;
    private String message;
    
    /**
     * gameID and is the expected return value for CreateGame
     */
    public CreateGameResult() {}
    
    public CreateGameResult(int gameID, String message) {
        this.gameID = gameID;
    }
    
    public int getGameID() {
        return gameID;
    }
    
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
