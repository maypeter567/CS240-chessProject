package console_interact;

import chess.ChessGame;
import org.jetbrains.annotations.NotNull;
import request.*;
import result.*;
import ui.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import Exception.ResponseException;
import webSocketMessages.userCommands.allCommands.JoinPlayerGameCommand;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class Client extends Endpoint {
    
    public static void main(String @NotNull [] args) {
        JoinResult joinResult = null;
        ListGamesResult listGamesResult = null;
        CreateGameResult createGameResult = null;
        RegisterResult register;
        LoginResult login = null;
        String gameName = "";
        String auth = "";
        String urlPath = "http://localhost:8080";
        if (args.length == 1) {
            urlPath = args[0];
        }
        boolean notLoggedIn = true;
        boolean run = true;
        System.out.printf("Welcome to Chess by P.M, type help for instructions.%n>>> ");
        Scanner scanner = new Scanner(System.in);
        while (run) {
            String line = scanner.nextLine();
            line = line.toLowerCase();
            
            var tokens = line.split(" ");
            
            var command = tokens[0];
            
            ServerFacade serverFacade = new ServerFacade(urlPath);
            WebSocketFacade ws;
            if (notLoggedIn) {
                switch (command) {
                    case "help":
                        new PrintHelpUnauthorized().print();
                        break;
                    case "quit":
                        run = false;
                        continue;
                    case "login":
                        LoginRequest loginRequest = new LoginRequest();
                        if (tokens.length == 3) {
                            loginRequest.setPassword(tokens[2]);
                            loginRequest.setUsername(tokens[1]);
                            try {
                                login = serverFacade.login(loginRequest);
                            } catch (ResponseException e){
                                System.out.println(e.getMessage());
                                System.out.println(">>> ");
                                continue;
                            }
                            System.out.println("you are now logged in! type help for a list of commands.");
                            notLoggedIn = false;
                        } else {
                            System.out.println("you have not provided enough arguments. type 'help' for a list of commands and how to use them.");
                        }
                        break;
                    case "register":
                        RegisterRequest registerRequest = new RegisterRequest();
                        if (tokens.length == 4) { // tokens are correct length
                            registerRequest.setUsername(tokens[1]);
                            registerRequest.setPassword(tokens[2]);
                            registerRequest.setEmail(tokens[3]);
                            try {
                                serverFacade.register(registerRequest);
                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.println(">>> ");
                                continue;
                            }
                            System.out.println("registration complete, the may now log in");
                        } else { // tokens are not correct length
                            System.out.println("you have not provided enough arguments. type 'help' for a list of commands and how to use them.");
                        }
                        break;
                    default:
                        System.out.println(command + " is not a valid command, please type 'help' for a list of commands");
                }
            } else {
                switch (command) {
                    case "help":
                        new PrintHelpAuthorized().print();
                        break;
                    case "logout":
                        if (tokens.length == 1) {
                            try {
                                LogoutResult logout = serverFacade.logout(login);
                            } catch (ResponseException e){
                                System.out.println(e.getMessage());
                                System.out.print(">>> ");
                                continue;
                            }
                            System.out.println("you are now logged out,  type help for a list of commands.");
                            notLoggedIn = true;
                        } else {
                            System.out.println("you have provided the incorrect number of arguments. type 'help' for a list of commands and how to use them.");
                        }
                        break;
                    case "create":
                        CreateGameRequest createGameRequest = new CreateGameRequest();
                        if (tokens.length == 2) {
                            createGameRequest.setGameName(tokens[1]);
                            try {
                                createGameResult = serverFacade.createGame(createGameRequest, login);
                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.print(">>> ");
                                continue;
                            }
                            System.out.println("you have successfully created a game, it's ID is: " + createGameResult.getGameID());
                        } else {
                            System.out.println("you have not provided enough arguments. type 'help' for a list of commands and how to use them.");
                        }
                        break;
                    case "list":
                        ListGamesRequest listGamesRequest = new ListGamesRequest();
                        if (tokens.length == 1) {
                            try {
                                listGamesResult = serverFacade.listGames(listGamesRequest, login);
                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.print(">>> ");
                                continue;
                            }
                            new PrintListGames().print(listGamesResult);
                        } else {
                            System.out.println("you have not provided enough arguments. type 'help' for a list of commands and how to use them.");
                        }
                        break;
                    case "join":
                        JoinRequest joinRequest1 = new JoinRequest();
                        if (tokens.length == 3) {
                            joinRequest1.setGameID(Integer.parseInt(tokens[1]));
                            joinRequest1.setPlayerColor(tokens[2]);
                            ChessGame.TeamColor teamColor;
                            if (Objects.equals(joinRequest1.getPlayerColor(), "WHITE")) {
                                teamColor = ChessGame.TeamColor.WHITE;
                            } else {
                                teamColor = ChessGame.TeamColor.BLACK;
                            }
                            try {
                                serverFacade.join(joinRequest1, login);
                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.print(">>> ");
                                continue;
                            }
                            try {
                                ws = new WebSocketFacade(urlPath);
                                JoinPlayerGameCommand joinPlayerGameCommand = new JoinPlayerGameCommand(auth, joinRequest1.getGameID(), teamColor);
                                boolean running = ws.isOpen();
                                ws.joinPlayerGame(joinPlayerGameCommand);
                            } catch (ResponseException | IOException e) {
                                System.out.println(e.getMessage());
                            }
                            ListGamesRequest listGamesRequest2 = new ListGamesRequest();
                            try {
                                listGamesResult = serverFacade.listGames(listGamesRequest2, login);
                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.print(">>> ");
                                continue;
                            }
                            for (var i : listGamesResult.getGames()) {
                                if (i.getGameID() == Integer.parseInt(tokens[1])) {
                                    new PrintJoinGame().print(i);
                                    break;
                                }
                            }
                        } else {
                            System.out.println("you have not provided enough arguments. type 'help' for a list of commands and how to use them.");
                        }
                        break;
                    case "observe":
                        JoinRequest joinRequest2 = new JoinRequest();
                        if (tokens.length == 2) {
                            joinRequest2.setGameID(Integer.parseInt(tokens[1]));
                            try {
                                serverFacade.join(joinRequest2, login);
                            } catch (ResponseException e) {
                                if (e.StatusCode() == 400) {
                                    System.out.println("the game you requested doesn't exist.");
                                    System.out.print(">>> ");
                                } else {
                                    System.out.println(e.getMessage());
                                    System.out.print(">>> ");
                                    continue;
                                }
                            }
                            ListGamesRequest listGamesRequest3 = new ListGamesRequest();
                            try {
                                listGamesResult = serverFacade.listGames(listGamesRequest3, login);
                            } catch (ResponseException e) {
                                System.out.println(e.getMessage());
                                System.out.print(">>> ");
                                continue;
                            }
                            for (var i : listGamesResult.getGames()) {
                                if (i.getGameID() == Integer.parseInt(tokens[1])) {
                                    new PrintJoinGame().print(i);
                                    break;
                                }
                            }
                        } else {
                            System.out.println("you have not provided enough arguments. type 'help' for a list of commands and how to use them.");
                        }
                        break;
                    case "quit":
                        try {
                            LogoutResult logout = serverFacade.logout(login);
                        } catch (ResponseException e) {
                            System.out.println(e.getMessage());
                            System.out.print(">>> ");
                            continue;
                        }
                        notLoggedIn = true;
                        run = false;
                        continue;
                    default:
                        System.out.println(command + " is not a valid command, please type 'help' for a list of commands");
                }
            }
            System.out.print(">>> ");
        }
    }
    
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    
    }
}
