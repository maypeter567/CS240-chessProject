package webSocketMessages.userCommands.allCommands;

import webSocketMessages.userCommands.UserGameCommand;

public class ResignGameCommand extends UserGameCommand {
    int gameID;
    public ResignGameCommand(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }
}
