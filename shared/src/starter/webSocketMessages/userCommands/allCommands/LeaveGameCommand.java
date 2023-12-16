package webSocketMessages.userCommands.allCommands;

import webSocketMessages.userCommands.UserGameCommand;

public class LeaveGameCommand extends UserGameCommand {
    int gameID;
    public LeaveGameCommand(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }
}
