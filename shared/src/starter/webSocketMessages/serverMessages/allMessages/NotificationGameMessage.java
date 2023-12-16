package webSocketMessages.serverMessages.allMessages;

import webSocketMessages.serverMessages.ServerMessage;

public class NotificationGameMessage extends ServerMessage {
    String message;
    public NotificationGameMessage(ServerMessageType type, String message) {
        super(type);
        this.serverMessageType = ServerMessageType.NOTIFICATION;
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
