package webSocketMessages.userCommands.allCommands;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserverGameCommand extends UserGameCommand {
    int gameID;
    public JoinObserverGameCommand(String authToken, int gameID) {
        super(authToken);
        this.commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameID;
    }
    
    public int getGameID() {
        return gameID;
    }
}
