package server;

import Handlers.*;
import server.webSocket.WebSocketHandler;
import spark.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class Server{
    private final WebSocketHandler webSocketHandler;
    
    public Server() {
        webSocketHandler = new WebSocketHandler();
    }
    
    public static void main(String[] args) {
        var server = new Server();
        try {
            configureDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        server.run();
    }
    
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }
    
    public static void configureDatabase() throws SQLException {
        try (var conn = getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();
            
            conn.setCatalog("chess");
            
            var createUserTable = """
                CREATE TABLE IF NOT EXISTS users (
                name VARCHAR(225) NOT NULL,
                userMod TEXT NOT NULL,
                PRIMARY KEY (name)
            )""";
            
            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
            
            var createGameTable = """
                    CREATE TABLE IF NOT EXISTS games (
                    gameID int NOT NULL AUTO_INCREMENT,
                    gameMod TEXT NOT NULL,
                    PRIMARY KEY (gameID)
                    )""";
            
            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }
            
            var createAuthTable = """
                    CREATE TABLE IF NOT EXISTS authTokens (
                    authTokenValue int NOT NULL AUTO_INCREMENT,
                    username VARCHAR(225) NOT NULL,
                    PRIMARY KEY (authTokenValue)
                    )""";
            
            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }
    
    public void run() {
        Spark.port(8080);
        
        Spark.externalStaticFileLocation("web");
        
        Spark.webSocket("/connect", webSocketHandler);
        
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