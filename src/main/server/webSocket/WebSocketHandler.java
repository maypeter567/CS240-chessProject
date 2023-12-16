package server.webSocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import data_access.GameDAO;
import deserializers.DeserializerUserGameCommand;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.serverMessages.allMessages.ErrorGameMessage;
import webSocketMessages.serverMessages.allMessages.LoadGameMessage;
import webSocketMessages.serverMessages.allMessages.NotificationGameMessage;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.userCommands.allCommands.*;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {
    
    GameDAO gameDAO = new GameDAO();
    
    
    private final ConnectionManager connections = new ConnectionManager();
    
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserGameCommand.class, new DeserializerUserGameCommand()).create();
        var userGameCommand = gson.fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(gson.fromJson(message, JoinPlayerGameCommand.class), session);
            case JOIN_OBSERVER -> joinObserver(gson.fromJson(message, JoinObserverGameCommand.class), session);
            case LEAVE -> leave(gson.fromJson(message, LeaveGameCommand.class), session);
            case MAKE_MOVE -> makeMove(gson.fromJson(message, MakeMoveGameCommand.class), session);
            case RESIGN -> resign(gson.fromJson(message, ResignGameCommand.class), session);
        }
    }
    
    private void joinPlayer(JoinPlayerGameCommand joinPlayerGameCommand, Session session) throws IOException {
        connections.add(joinPlayerGameCommand.getAuthString(), session); // store the websocket object
        LoadGameMessage loadGameMessage;
        try {
            loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, new Gson()
                    .toJson(gameDAO.find(joinPlayerGameCommand.getGameID())));
            session.getRemote().sendString(new Gson().toJson(loadGameMessage));
            NotificationGameMessage notification = new NotificationGameMessage(ServerMessage.ServerMessageType.NOTIFICATION, "test1 joined the game as a human (maybe)");
//            System.out.println("test1");
            connections.broadcast(joinPlayerGameCommand.getAuthString(), notification);
        } catch (DataAccessException e) {
            ErrorGameMessage errorGameMessage = new ErrorGameMessage(ServerMessage.ServerMessageType.ERROR, e.getMessage());
            session.getRemote().sendString(new Gson().toJson(errorGameMessage));
        }
//        System.out.println("test6");
    }
    
    private void joinObserver(JoinObserverGameCommand joinObserverGameCommand, Session session) throws IOException {
        connections.add(joinObserverGameCommand.getAuthString(), session);
        LoadGameMessage loadGameMessage;
        try {
            loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, new Gson()
                    .toJson(gameDAO.find(joinObserverGameCommand.getGameID())));
        } catch (DataAccessException e) {
            loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, e.getMessage());
        }
        session.getRemote().sendString(new Gson().toJson(loadGameMessage));
        NotificationGameMessage notification = new NotificationGameMessage(ServerMessage.ServerMessageType.NOTIFICATION, "test2 joined the game as an observer");
        connections.broadcast(joinObserverGameCommand.getAuthString(), notification);
    }
    
    private void leave(LeaveGameCommand leaveGameCommand, Session session) throws IOException {
        connections.remove(leaveGameCommand.getAuthString());
        NotificationGameMessage notificationGameMessage = new NotificationGameMessage(ServerMessage.ServerMessageType.NOTIFICATION, "test3 left the game");
        connections.broadcast(null, notificationGameMessage);
        //TODO
    }
    
    private void makeMove(MakeMoveGameCommand makeMoveGameCommand, Session session) {
        
        //TODO
    }
    
    private void resign(ResignGameCommand resignGameCommand, Session session) {
        //TODO
    }
    
//    private void exit(String visitorName) throws IOException {
//        connections.remove(visitorName);
//        var message = String.format("%s left the shop", visitorName);
//        var notification = new Notification(Notification.Type.DEPARTURE, message);
//        connections.broadcast(visitorName, notification);
//    }
//
//    public void makeNoise(String petName, String sound) throws ResponseException {
//        try {
//            var message = String.format("%s says %s", petName, sound);
//            var notification = new Notification(Notification.Type.NOISE, message);
//            connections.broadcast("", notification);
//        } catch (Exception ex) {
//            throw new ResponseException(500, ex.getMessage());
//        }
//    }
}