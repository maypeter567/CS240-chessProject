package request;

public class CreateGameRequest {
    private String gameName;
    
    /**
     * this class keeps track of gameName for createGame requests
     */
    public CreateGameRequest() {}
    
    public String getGameName() {
        return gameName;
    }
    
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
