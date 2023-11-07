package data_access;

import chess.*;
import chess.pieces.*;
import com.google.gson.*;
import dataAccess.DataAccessException;
import models.GameMod;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GameDAO {
    
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
                    return deserializer(rs.getString("gameMod"));
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
                    allGames.put(Integer.parseInt(rs.getString("gameID")), deserializer(rs.getString("gameMod")));
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
     * @param game the updated game
     * @param gameID the id of the game stored in the database
     * @throws DataAccessException if the game doesn't exist, or the game is unmodifiable
     */
    public final void updateGame(int gameID, GameMod game) throws DataAccessException {
        if (find(gameID) == null) {
            throw new DataAccessException("the game you are trying to update doesn't exist");
        }
        String jsonGame = new Gson().toJson(game);
        
        try (var conn = getConnection()) {
            conn.setCatalog("chess");
            
            var updateGame = """
                UPDATE games SET gameMod = ? WHERE gameID = ?;
                """;
            
            try (var preparedStatement = conn.prepareStatement(updateGame)) {
                preparedStatement.setString(1, jsonGame);
                preparedStatement.setString(2, Integer.toString(gameID));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
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
    
    private GameMod deserializer(String stringToChange) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(ChessGameImp.class, new GameAdapter());
        return gsonBuilder.create().fromJson(stringToChange, GameMod.class); // I WAS WORKING HERE
    }
    
    private class GameAdapter implements JsonDeserializer<ChessGameImp> {
        
        @Override
        public ChessGameImp deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeAdapter(ChessBoardImp.class, new BoardAdapter());
            return gsonBuilder.create().fromJson(jsonElement, ChessGameImp.class);
        }
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
            GsonBuilder gson = new GsonBuilder();
            String pieceType = jsonElement.getAsJsonObject().get("type").getAsString();
            return switch (pieceType) {
                case "QUEEN" -> gson.create().fromJson(jsonElement, Queen.class);
                case "KING" -> gson.create().fromJson(jsonElement, King.class);
                case "KNIGHT" -> gson.create().fromJson(jsonElement, Knight.class);
                case "BISHOP" -> gson.create().fromJson(jsonElement, Bishop.class);
                case "ROOK" -> gson.create().fromJson(jsonElement, Rook.class);
                default -> gson.create().fromJson(jsonElement, Pawn.class);
            };
        }
    }
}
