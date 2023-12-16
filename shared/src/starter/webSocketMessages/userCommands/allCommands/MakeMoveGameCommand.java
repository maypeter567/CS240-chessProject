package webSocketMessages.userCommands.allCommands;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMoveGameCommand extends UserGameCommand {
    int gameID;
    ChessMove move;
    public MakeMoveGameCommand(String authToken, int gameID, ChessMove move) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
    }
}
