package serverFacadeTests;

import models.AuthTokenMod;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import request.*;
import result.*;
import ui.ServerFacade;
import Exception.ResponseException;

import java.util.Objects;
import java.util.Scanner;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerFacadeTest {
    ServerFacade serverFacade = new ServerFacade("http://localhost:8080");
    
    @Test
    @Order(0)
    void setUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("clear the databases for test to run unhindered, then press enter.");
        scanner.nextLine();
    }
    
    @Test
    @Order(1)
    void registerPass() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test10");
        registerRequest.setEmail("email");
        registerRequest.setPassword("pass");
        try {
            RegisterResult registerResult = serverFacade.register(registerRequest);
            Assertions.assertEquals(registerResult.getUsername(), registerRequest.getUsername());
        } catch (ResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    @Order(2)
    void registerFail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("test10");
        registerRequest.setEmail("email");
        registerRequest.setPassword("pass");
        try {
            RegisterResult registerResult = serverFacade.register(registerRequest);
            Assertions.fail();
        } catch (ResponseException e) {
            e.getMessage();
        }
    }
    
    @Test
    @Order(3)
    void loginPass() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test10");
        loginRequest.setPassword("pass");
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            Assertions.assertEquals(loginResult.getUsername(), loginRequest.getUsername());
        } catch (ResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    @Order(4)
    void loginFail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test10");
        loginRequest.setPassword("passed");
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            Assertions.fail();
        } catch (ResponseException e) {
            e.getMessage();
        }
    }
    
    @Test
    @Order(5)
    void logoutPass() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test10");
        loginRequest.setPassword("pass");
        
        LogoutResult logoutResult;
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            logoutResult = serverFacade.logout(loginResult);
        } catch (ResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    @Order(6)
    void logoutFail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test10");
        loginRequest.setPassword("pass");
        
        LogoutResult logoutResult;
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            loginResult.setAuthToken(loginResult.getAuthToken()+100);
            logoutResult = serverFacade.logout(loginResult);
            Assertions.fail();
        } catch (ResponseException e) {
            e.getMessage();
        }
    }
    
    @Test
    @Order(7)
    void createGamePass() {
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("alpha");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass");
        loginRequest.setUsername("test10");
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            CreateGameResult createGameResult = serverFacade.createGame(createGameRequest, loginResult);
            Assertions.assertNull(createGameResult.getMessage());
        } catch (ResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    @Order(8)
    void createGameFail() {
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("alpha");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass");
        loginRequest.setUsername("test10");
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            loginResult.setAuthToken(loginResult.getAuthToken()+1000);
            CreateGameResult createGameResult = serverFacade.createGame(createGameRequest, loginResult);
            Assertions.fail();
        } catch (ResponseException e) {
            e.getMessage();
        }
    }
    
    @Test
    @Order(9)
    void listGamesPass() {
        ListGamesRequest listGamesRequest = new ListGamesRequest();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test10");
        loginRequest.setPassword("pass");
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            ListGamesResult listGamesResult = serverFacade.listGames(listGamesRequest, loginResult);
            Assertions.assertEquals(listGamesResult.getGames().size(), 1);
        } catch (ResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    @Order(10)
    void listGamesFail() {
        ListGamesRequest listGamesRequest = new ListGamesRequest();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test10");
        loginRequest.setPassword("pass");
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            ListGamesResult listGamesResult = serverFacade.listGames(listGamesRequest, loginResult);
            Assertions.assertNotEquals(listGamesResult.getGames().size(), 4);
        } catch (ResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    @Order(11)
    void joinPass() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass");
        loginRequest.setUsername("test10");
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("test game");
        JoinRequest joinRequest = new JoinRequest();
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            CreateGameResult createGameResult = serverFacade.createGame(createGameRequest, loginResult);
            joinRequest.setAuthToken(new AuthTokenMod(loginResult.getAuthToken(), "test10"));
            joinRequest.setGameID(createGameResult.getGameID());
            joinRequest.setPlayerColor("WHITE");
            
            JoinResult joinResult = serverFacade.join(joinRequest, loginResult);
            Assertions.assertEquals(joinResult.getMessage(), "you are now the white player");
        } catch (ResponseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    @Order(12)
    void joinFail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass");
        loginRequest.setUsername("test10");
        CreateGameRequest createGameRequest = new CreateGameRequest();
        createGameRequest.setGameName("test game");
        JoinRequest joinRequest = new JoinRequest();
        try {
            LoginResult loginResult = serverFacade.login(loginRequest);
            CreateGameResult createGameResult = serverFacade.createGame(createGameRequest, loginResult);
            joinRequest.setAuthToken(new AuthTokenMod(loginResult.getAuthToken(), "test10"));
            joinRequest.setGameID(createGameResult.getGameID()*1000);
            joinRequest.setPlayerColor("WHITE");
            
            JoinResult joinResult = serverFacade.join(joinRequest, loginResult);
            Assertions.fail();
        } catch (ResponseException e) {
            e.getMessage();
        }
    }
}