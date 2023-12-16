package webSocketMessages.serverMessages.allMessages;

import webSocketMessages.serverMessages.ServerMessage;

public class ErrorGameMessage extends ServerMessage {
    String errorMessage;
    public ErrorGameMessage(ServerMessageType type, String errorMessage) {
        super(type);
        this.serverMessageType = ServerMessageType.ERROR;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
