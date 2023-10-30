package serviceTests;

import dataAccess.DataAccessException;
import data_access.*;
import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;
import request.*;
import result.*;

import java.util.Collection;
import java.util.Map;
import java.util.Stack;

class ServiceTests {
    
    AuthDAO authDAO = new AuthDAO();
    GameDAO gameDAO = new GameDAO();
    UserDAO userDAO = new UserDAO();
    Stack<Integer> stack = new Stack<>();
    
    @Test
    void clear() {
        AuthTokenMod test1;
        try {
            test1 = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        int test2;
        try {
            test2 = gameDAO.createNewGame("test2");
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        try {
            userDAO.Insert(new UserMod("test3", "pass", "email"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        try {
            authDAO.clear();
            gameDAO.clear();
            userDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            if (authDAO.checkVerified(test1)) {
                Assertions.fail("authDAO clear did not clear properly");
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        try {
            if (gameDAO.find(test2) != null) {
                Assertions.fail("gameDAO clear did not clear properly");
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        if (userDAO.containsUser("test3")) {
            Assertions.fail("userDAO clear did not clear properly");
        }
    }
    
    @BeforeEach
    public void setUp() {
        try {
            authDAO.clear();
            gameDAO.clear();
            userDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        stack.clear();
        
        try {
            userDAO.Insert(new UserMod("test1", "pass", "email"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void createGameTestPass() {
        CreateGameRequest testRequest = new CreateGameRequest();
        testRequest.setGameName("test1");
        
        AuthTokenMod testToken = null;
        try {
            testToken = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        String gameName = "testName";
        testRequest.setGameName(gameName);
        
        CreateGameResult testResult;
        try {
            testResult = new CreateGame().createGame(testRequest, testToken, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Map<Integer, GameMod> allGames;
        try {
            allGames = gameDAO.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(allGames.keySet().toArray()[0], testResult.getGameID());
    }
    
    @Test
    public void createGameTestFail() {
        CreateGameRequest testRequest = new CreateGameRequest();
        testRequest.setGameName("test1");
        
        AuthTokenMod testToken = null;
        try {
            testToken = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        String gameName = "testName";
        testRequest.setGameName(gameName);
        
        CreateGameResult testResult;
        try {
            testResult = new CreateGame().createGame(testRequest, testToken, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Map<Integer, GameMod> allGames;
        try {
            allGames = gameDAO.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(allGames.keySet().toArray()[0], 700*45);
    }
    
    @Test
    public void joinTestPass() {
        JoinRequest joinRequest = new JoinRequest();
        
        try {
            int testGameID = gameDAO.createNewGame("testGame");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        AuthTokenMod testToken = null;
        try {
            testToken = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        joinRequest.setAuthToken(testToken);
        
        
    }
}