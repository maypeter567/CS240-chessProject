package server.webSocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public String authToken;
    public Session session;
    
    public Connection(String authToken, Session session) {
        this.authToken = authToken;
        this.session = session;
    }
    
    public void send(String msg) throws IOException {
//        System.out.println("test4");
        session.getRemote().sendString(msg);
//        System.out.println("test5");
    }
}