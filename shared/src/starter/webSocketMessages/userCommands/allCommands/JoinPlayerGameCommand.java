package webSocketMessages.userCommands.allCommands;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinPlayerGameCommand extends UserGameCommand {
    int gameID;
    ChessGame.TeamColor playerColor;
    public JoinPlayerGameCommand(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }
    
    public int getGameID() {
        return this.gameID;
    }
}
