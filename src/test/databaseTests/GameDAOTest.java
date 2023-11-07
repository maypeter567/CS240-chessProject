package databaseTests;

import chess.ChessGameImp;
import dataAccess.DataAccessException;
import data_access.GameDAO;
import models.GameMod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

class GameDAOTest {
    
    private final GameDAO gameDAO = new GameDAO();
    
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }
    
    @BeforeEach
    public void setup() {
        try {
            gameDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void insertPass() {
        try (var conn = getConnection()) {
            
            GameMod testGame = new GameMod(1, null, null, "test", new ChessGameImp());
            gameDAO.insert(testGame);
            
            conn.setCatalog("chess");
            
            var testInsert = """
                SELECT gameID, gameMod FROM games WHERE gameID=1
                """;
            
            try(var preparedStatement = conn.prepareStatement(testInsert)) {
                var rs = preparedStatement.executeQuery();
                
                if (rs.next()) {
                    var id = rs.getString("gameID");
                    var game = rs.getString("gameMod");
                    
                    Assertions.assertEquals(id, Integer.toString(1));
                } else {
                    Assertions.fail("the authToken was never made");
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void insertFail() {
        try (var conn = getConnection()) {
            for (int i = 1; i < 15; i++) {
                GameMod testGame = new GameMod(i, null, null, "test", new ChessGameImp());
                gameDAO.insert(testGame);
                
                conn.setCatalog("chess");
                
                var testInsert = """
                            
                            SELECT gameID, gameMod FROM games WHERE gameID=?
                        """;
                
                try (var preparedStatement = conn.prepareStatement(testInsert)) {
                    preparedStatement.setString(1, Integer.toString(i));
                    var rs = preparedStatement.executeQuery();
                    
                    if (rs.next()) {
                        var id = rs.getString("gameID");
                        var game = rs.getString("gameMod");
                        
                        Assertions.assertEquals(id, Integer.toString(i));
                    } else {
                        Assertions.fail("the authToken was never made");
                    }
                }
            }
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        
//        try {
//            Assertions.assertThrows(DataAccessException.class, (Executable) gameDAO.insert(testGame));
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e.getMessage());
//        }
    }
    
    @Test
    void findPass() {
        try {
            GameMod testGame = new GameMod(1, null, null, "test", new ChessGameImp());
            gameDAO.insert(testGame);
            
            Assertions.assertEquals(testGame.getGameID(), Objects.requireNonNull(gameDAO.find(1)).getGameID());
            
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void findFail() {
        try {
            
            Assertions.assertNull(gameDAO.find(1));
            
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void findAllPass() {
        try {
            gameDAO.insert(new GameMod(1, null, null, "test", new ChessGameImp()));
            gameDAO.insert(new GameMod(2, null, null, "test", new ChessGameImp()));
            gameDAO.insert(new GameMod(3, null, null, "test", new ChessGameImp()));
            gameDAO.insert(new GameMod(4, null, null, "test", new ChessGameImp()));
            
            Map<Integer, GameMod> map = gameDAO.findAll();
            
            Assertions.assertTrue(map.containsKey(1));
            Assertions.assertTrue(map.containsKey(2));
            Assertions.assertTrue(map.containsKey(3));
            Assertions.assertTrue(map.containsKey(4));
            
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void findAllFail() { // BRANDON AND NOAH SAID IT'S CHILL
        try {
            for (int i = 1; i < 15; i++) {
                gameDAO.insert(new GameMod(i, null, null, "test", new ChessGameImp()));
            }
            
            Map<Integer, GameMod> map = gameDAO.findAll();
            
            for (int i = 1; i < 15; i++) {
                Assertions.assertTrue(map.containsKey(i));
            }
            
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void createNewGamePass() {
        try {
            int test = gameDAO.createNewGame("test");
            
            Assertions.assertEquals(Objects.requireNonNull(gameDAO.find(test)).getGameID(), test);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void createNewGameFail() {
        try {
            int test = gameDAO.createNewGame("test");
            
            Assertions.assertNotEquals(Objects.requireNonNull(gameDAO.find(test)).getGameID(), test+1);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void updateGamePass() {
        try {
            int gameID = gameDAO.createNewGame("test");
            GameMod game1 = gameDAO.find(gameID);
            assert game1 != null;
            game1.setWhiteUsername("george");
            gameDAO.updateGame(gameID, game1);
            
            GameMod game2 = gameDAO.find(gameID);
            
            assert game2 != null;
            Assertions.assertEquals(game2.getWhiteUsername(), "george");
        } catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void updateGameFail() {
        try {
            int gameID = gameDAO.createNewGame("test");
            GameMod game1 = gameDAO.find(gameID);
            assert game1 != null;
            game1.setWhiteUsername("peter");
            gameDAO.updateGame(gameID, game1);
            
            GameMod game2 = gameDAO.find(gameID);
            
            assert game2 != null;
            Assertions.assertEquals(game2.getWhiteUsername(), "peter");
            
            gameID = gameDAO.createNewGame("test");
            game1 = gameDAO.find(gameID);
            assert game1 != null;
            game1.setWhiteUsername("george");
            gameDAO.updateGame(gameID, game1);
            
            game2 = gameDAO.find(gameID);
            
            assert game2 != null;
            Assertions.assertEquals(game2.getWhiteUsername(), "george");
            
            gameID = gameDAO.createNewGame("test");
            game1 = gameDAO.find(gameID);
            assert game1 != null;
            game1.setWhiteUsername("holabud");
            gameDAO.updateGame(gameID, game1);
            
            game2 = gameDAO.find(gameID);
            
            assert game2 != null;
            Assertions.assertEquals(game2.getWhiteUsername(), "holabud");
            
            gameID = gameDAO.createNewGame("test");
            game1 = gameDAO.find(gameID);
            assert game1 != null;
            game1.setWhiteUsername("science");
            gameDAO.updateGame(gameID, game1);
            
            game2 = gameDAO.find(gameID);
            
            assert game2 != null;
            Assertions.assertEquals(game2.getWhiteUsername(), "science");
            
            gameID = gameDAO.createNewGame("test");
            game1 = gameDAO.find(gameID);
            assert game1 != null;
            game1.setWhiteUsername("elmo");
            gameDAO.updateGame(gameID, game1);
            
            game2 = gameDAO.find(gameID);
            
            assert game2 != null;
            Assertions.assertEquals(game2.getWhiteUsername(), "elmo");
        } catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void clear() {
        try {
            gameDAO.insert(new GameMod(1, null, null, "test", new ChessGameImp()));
            
            gameDAO.clear();
            
            Assertions.assertNull(gameDAO.find(1));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
    }
}