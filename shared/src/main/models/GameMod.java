package models;

import chess.ChessGameImp;

public class GameMod {
    private final int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private final ChessGameImp game;
    
    /**
     * handles running the game
     *
     * @param gameID a way to find the game it's playing in the database
     * @param whiteUserName the name of player 1
     * @param blackUserName the name of player 2
     * @param gameName a name to be displayed in the browser
     * @param game the game itself, which makes moves and keeps the rules.
     */
    public GameMod(int gameID, String whiteUserName, String blackUserName, String gameName, ChessGameImp game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUserName;
        this.blackUsername = blackUserName;
        this.gameName = gameName;
        this.game = game;
    }
    
    public int getGameID() { return gameID; }
    
    public String getWhiteUsername() {
        return whiteUsername;
    }
    
    public void setWhiteUsername(String username) {
        this.whiteUsername = username;
    }
    
    public String getBlackUsername() {
        return blackUsername;
    }
    
    public void setBlackUsername(String username) {
        this.blackUsername = username;
    }
    
    public String getGameName() {
        return gameName;
    }
    
    public ChessGameImp getGame() {
        return game;
    }
}
