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
        
        try {
            if (userDAO.containsUser("test3")) {
                Assertions.fail("userDAO clear did not clear properly");
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
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
            testResult = new CreateGame().createGame(testRequest, new AuthTokenMod("frog", "frog"), stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Map<Integer, GameMod> allGames;
        try {
            allGames = gameDAO.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 401);
    }
    
    @Test
    public void joinTestPass() {
        JoinRequest joinRequest = new JoinRequest();
        Stack<Integer> stack = new Stack<Integer>();
        
        int testGameID;
        try {
            testGameID = gameDAO.createNewGame("testGame");
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
        joinRequest.setGameID(testGameID);
        joinRequest.setPlayerColor("WHITE");
        
        JoinResult joinResult;
        try {
            joinResult = new Join().join(joinRequest, testToken, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 200);
    }
    
    @Test
    public void joinTestFail() {
        JoinRequest joinRequest = new JoinRequest();
        Stack<Integer> stack = new Stack<Integer>();
        
        int testGameID;
        try {
            testGameID = gameDAO.createNewGame("testGame");
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
        joinRequest.setGameID(testGameID+4001);
        joinRequest.setPlayerColor("WHITE");
        
        JoinResult joinResult;
        try {
            joinResult = new Join().join(joinRequest, testToken, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 400);
    }
    
    @Test
    public void listGamesPass() {
        JoinRequest joinRequest = new JoinRequest();
        Stack<Integer> stack = new Stack<>();
        
        int testGameID;
        try {
            testGameID = gameDAO.createNewGame("testGame");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        AuthTokenMod testToken = null;
        try {
            testToken = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        ListGamesResult listGamesResult;
        try {
            listGamesResult = new ListGames().listGames(testToken, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        Assertions.assertEquals(stack.lastElement(), 200);
    }
    
    @Test
    public void listGamesFail() {
        JoinRequest joinRequest = new JoinRequest();
        Stack<Integer> stack = new Stack<>();
        
        int testGameID;
        try {
            testGameID = gameDAO.createNewGame("testGame");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        AuthTokenMod testToken = null;
        try {
            testToken = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        ListGamesResult listGamesResult;
        try {
            listGamesResult = new ListGames().listGames(new AuthTokenMod("frog", "frog"), stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        
        Assertions.assertEquals(stack.lastElement(), 401);
    }
    
    @Test
    public void loginResultPass() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test1");
        loginRequest.setPassword("pass");
        
        Stack<Integer> stack = new Stack<>();
        
        LoginResult loginResult;
        try {
            loginResult = new Login().login(loginRequest, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 200);
    }
    
    @Test
    public void loginResultFail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test1");
        loginRequest.setPassword("password");
        
        Stack<Integer> stack = new Stack<>();
        
        LoginResult loginResult;
        try {
            loginResult = new Login().login(loginRequest, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 401);
    }
    
    @Test
    public void logoutPass() {
        LogoutRequest logoutRequest = new LogoutRequest();
        Stack<Integer> stack = new Stack<>();
        
        AuthTokenMod testToken = null;
        try {
            testToken = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        LogoutResult logoutResult;
        try {
            logoutResult = new Logout().logout(logoutRequest, testToken, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 200);
    }
    
    @Test
    public void logoutFail() {
        LogoutRequest logoutRequest = new LogoutRequest();
        Stack<Integer> stack = new Stack<>();
        
        AuthTokenMod testToken = null;
        try {
            testToken = authDAO.getAuthToken("test1");
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        LogoutResult logoutResult;
        try {
            logoutResult = new Logout().logout(logoutRequest, new AuthTokenMod("froggy", "pall"), stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 401);
    }
    
    @Test
    public void registerPass() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("pass");
        registerRequest.setUsername("test11");
        registerRequest.setEmail("email");
        Stack<Integer> stack = new Stack<>();
        
        RegisterResult registerResult;
        try {
            registerResult = new Register().register(registerRequest, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 200);
    }
    
    @Test
    public void registerFail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("password");
        registerRequest.setUsername("test1");
        registerRequest.setEmail("email");
        Stack<Integer> stack = new Stack<>();
        
        RegisterResult registerResult;
        try {
            registerResult = new Register().register(registerRequest, stack);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        
        Assertions.assertEquals(stack.lastElement(), 403);
    }
}