package models;

import chess.ChessGameImp;

public class GameMod {
    private final int gameID;
    private final String whiteUserName;
    private final String blackUserName;
    private final String gameName;
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
        this.whiteUserName = whiteUserName;
        this.blackUserName = blackUserName;
        this.gameName = gameName;
        this.game = game;
    }
    
    public int getGameID() { return gameID; }
    
    public String getWhiteUserName() {
        return whiteUserName;
    }
    
    public String getBlackUserName() {
        return blackUserName;
    }
    
    public String getGameName() {
        return gameName;
    }
    
    public ChessGameImp getGame() {
        return game;
    }
}
