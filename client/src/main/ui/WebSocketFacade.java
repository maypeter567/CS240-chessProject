package ui;

import com.google.gson.Gson;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import Exception.ResponseException;
import com.google.gson.GsonBuilder;
import deserializers.DeserializerGameMod;
import deserializers.DeserializerServerMessage;
import models.GameMod;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.allMessages.ErrorGameMessage;
import webSocketMessages.serverMessages.allMessages.LoadGameMessage;
import webSocketMessages.serverMessages.allMessages.NotificationGameMessage;
import webSocketMessages.userCommands.allCommands.JoinPlayerGameCommand;

public class WebSocketFacade extends Endpoint {
    
    Session session;
//    NotificationHandler notificationHandler;
    
    public WebSocketFacade(String url) throws ResponseException {
        try {
//            this.notificationHandler = notificationHandler;
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);
            
            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    Gson gson = new GsonBuilder().registerTypeAdapter(ServerMessage.class, new DeserializerServerMessage()).create();
                    ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case NOTIFICATION -> notification(gson.fromJson(message, NotificationGameMessage.class));
                        case ERROR -> error(gson.fromJson(message, ErrorGameMessage.class));
                        case LOAD_GAME -> loadGame(gson.fromJson(message, LoadGameMessage.class));
                        default -> System.out.println("the message received didn't match any known type.");
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    
    public boolean isOpen() {
        return this.session.isOpen();
    }
    
    private void notification(NotificationGameMessage notificationGameMessage) {
        System.out.println(notificationGameMessage.getMessage());
        System.out.println("notification was called in wsf");
    }
    
    private void error(ErrorGameMessage errorGameMessage) {
        System.out.println(errorGameMessage.getErrorMessage()); System.out.println("error was called in wsf");
    }
    
    private void loadGame(LoadGameMessage loadGameMessage) {
        Gson gson = new GsonBuilder().registerTypeAdapter(GameMod.class, new DeserializerGameMod()).create();
        GameMod game = gson.fromJson(loadGameMessage.getGame(), GameMod.class);
        System.out.println("loadGame was called in wsf");
    }
    
    public void joinPlayerGame(JoinPlayerGameCommand joinPlayerGameCommand) throws IOException {
        session.getBasicRemote().sendText(new Gson().toJson(joinPlayerGameCommand));
        System.out.println("join player was called in wsf");
    }
    
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {}
}
