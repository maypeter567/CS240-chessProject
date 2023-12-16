package webSocketMessages.serverMessages.allMessages;

import models.GameMod;
import webSocketMessages.serverMessages.ServerMessage;

public class LoadGameMessage extends ServerMessage {
    String game;
    public LoadGameMessage(ServerMessageType type, String game) {
        super(type);
        this.serverMessageType = ServerMessageType.LOAD_GAME;
        this.game = game;
    }
    
    public String getGame() {
        return this.game;
    }
}
