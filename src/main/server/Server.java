package server;

import Handlers.*;
import spark.*;

public class Server{
    public static void main(String[] args) {
        var server = new Server();
        server.run();
    }
    
    public void run() {
        Spark.port(8080);
        
        Spark.externalStaticFileLocation("web");
        
        Spark.delete("/db", new ClearHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.put("/game", new JoinHandler());
        Spark.get("/game", new ListGamesHandler());
        Spark.post("/session", new LoginHandler());
        Spark.delete("/session", new LogoutHandler());
        Spark.post("/user", new RegisterHandler());
        
        Spark.awaitInitialization();
    }
}