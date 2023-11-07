package data_access;

import chess.*;
import com.google.gson.*;
import dataAccess.DataAccessException;
import dataAccess.Database;
import models.AuthTokenMod;
import models.GameMod;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class GameDAO {
    
    private final static Map<Integer, GameMod> games = new HashMap<>();
    static int i = 0;
    
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }
    
    /**
     * inserts a new game into the database.
     *
     * @param newGame the game to be added.
     * @throws DataAccessException throws if adding failed.
     */
    public final void insert(GameMod newGame) throws DataAccessException {
        if (find(newGame.getGameID()) != null) {
            throw new DataAccessException("This ID is already used by an existing game.");
        } else {
            try(var conn = getConnection()) {
                
                conn.setCatalog("chess");
                
                var createNewGame = """
                INSERT INTO games (gameMod, gameID) VALUES (?, ?)
                """;
//            System.out.println(createNewAuthToken);
                
                var gson = new Gson();
                
                
                try(var preparedStatement = conn.prepareStatement(createNewGame)) {
                    preparedStatement.setString(1, gson.toJson(newGame));
                    preparedStatement.setString(2, String.valueOf(newGame.getGameID()));
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }
    }
    
    /**
     * retrieves a game by gameID
     *
     * @param gameID the unique number of a game to access
     * @return the game to be played
     * @throws DataAccessException throws if the game doesn't exist, or connection error
     */
    public final GameMod find(int gameID) throws DataAccessException {
        try(var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var findGame = """
                SELECT gameID, gameMod FROM games WHERE gameID=?
                """;
//            System.out.println(createNewAuthToken);
            
            try(var preparedStatement = conn.prepareStatement(findGame)) {
                preparedStatement.setString(1, String.valueOf(gameID));
                var rs = preparedStatement.executeQuery();
                
                if (rs.next()) {
                    Gson gson = new Gson();
                    return gson.fromJson(rs.getString("gameMod"), GameMod.class);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
//        return games.getOrDefault(gameID, null);
    }
    
    /**
     * returns all games in a data structure
     *
     * @return a collection of all games
     * @throws DataAccessException if the connection fails
     */
    public final Map<Integer, GameMod> findAll() throws DataAccessException {
        Map<Integer, GameMod> allGames = new HashMap<>();
        try(var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var findGame = """
                SELECT * FROM games
                """;
//            System.out.println(createNewAuthToken);
            
            try(var preparedStatement = conn.prepareStatement(findGame)) {
                var rs = preparedStatement.executeQuery();
                
                while (rs.next()) {
                    Gson gson = new Gson();
                    allGames.put(Integer.parseInt(rs.getString("gameID")),gson.fromJson(rs.getString("gameMod"), GameMod.class));
                }
                return allGames;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    
    /**
     * a method for claiming a spot in a game as a player
     *
     * @param username the name of the player to join
     * @throws DataAccessException if the game is already full, no longer exists, or bad connection.
     */
    public final void claimSpot(String username) throws DataAccessException {
        // I am going to have to implement a decently complex method for this...
        throw new DataAccessException("this is not implemented");
    }
    
    /**
     * updates the gameID to another value in the database
     *
     * @param gameID the new value to be added
     * @throws DataAccessException if the game doesn't exist, or the game is unmodifiable
     */
    public final void updateGame(String gameID) throws DataAccessException {
        throw new DataAccessException("this is not implemented");
    }
    
    public int createNewGame(String gameName) throws DataAccessException {
        int gameID = gameIDGenerator();
        insert(new GameMod(gameID, null, null, gameName, new ChessGameImp()));
        return gameID;
    }
    
    private int gameIDGenerator() {
        i++;
        return i;
    }
    
    public void clear() throws DataAccessException {
        try (var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var dropGames = """
                                DROP TABLE IF EXISTS games
                        """;
//            System.out.println(createNewAuthToken);
            
            try (var preparedStatement = conn.prepareStatement(dropGames)) {
                preparedStatement.executeUpdate();
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
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    
    private ChessGameImp deserializer(String stringToChange) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(ChessBoard.class, new BoardAdapter());
        return gsonBuilder.create().fromJson(stringToChange); // I WAS WORKING HERE
    }
    
    private class BoardAdapter implements JsonDeserializer<ChessBoard> {
        
        @Override
        public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeAdapter(ChessPiece.class, new PieceAdapter());
            return gsonBuilder.create().fromJson(jsonElement, ChessBoardImp.class);
        }
    }
    
    private class PieceAdapter implements JsonDeserializer<ChessPiece> {
        
        @Override
        public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String pieceType = jsonElement.getAsJsonObject().get("type").getAsString();
            // make a switch case or chain of if statements that will determine the class to generate from the piece
            return null;
        }
    }
}
