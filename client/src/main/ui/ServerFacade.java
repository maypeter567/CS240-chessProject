package ui;

import chess.ChessPiece;
import com.google.gson.*;
import deserializers.PieceAdapter;
import request.*;
import result.*;
import Exception.ResponseException;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class ServerFacade {
    private final String serverUrl;
    
    public ServerFacade(String url) {
        serverUrl = url;
    }
    
    
    public RegisterResult register(RegisterRequest request) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, request, RegisterResult.class, null);
    }
    
    public LoginResult login(LoginRequest request) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, request, LoginResult.class, null);
    }
    
    public LogoutResult logout(LoginResult login) throws ResponseException {
        var path = "/session";
        return this.makeRequest("DELETE", path, login, LogoutResult.class, login);
    }
    
    public CreateGameResult createGame(CreateGameRequest createGameRequest, LoginResult login) throws ResponseException {
        var path = "/game";
        return this.makeRequest("POST", path, createGameRequest, CreateGameResult.class, login);
    }
    
    public ListGamesResult listGames(ListGamesRequest listGamesRequest, LoginResult login) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, listGamesRequest, ListGamesResult.class, login);
    }
    
    public JoinResult join(JoinRequest joinRequest, LoginResult login) throws ResponseException {
        var path = "/game";
        
        return this.makeRequest("PUT", path, joinRequest, JoinResult.class, login);
    }
    
//    public Pet[] listPets() throws ResponseException {
//        var path = "/pet";
//        record listPetResponse(Pet[] pet) {
//        }
//        var response = this.makeRequest("GET", path, null, listPetResponse.class);
//        return response.pet();
//    }
    
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, LoginResult login) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            
            if (login != null) {
                http.addRequestProperty("authorization", login.getAuthToken());
            }
            
            if (!Objects.equals(method, "GET")) {
                http.setDoOutput(true);
                writeBody(request, http);
            }
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    
    
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }
    
    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            if (status == 403) {
                throw new ResponseException(status, "the user already exists");
            }
            throw new ResponseException(status, "failure: " + status);
        }
    }
    
    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
//                    Gson gson = new Gson();
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(ChessPiece.class, new PieceAdapter())
                            .create();
                    response = gson.fromJson(reader, responseClass);
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return response;
    }
    
    // result vector<GameMods>
    
    
    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
    
//    PrintCreateGame printCreateGame = new PrintCreateGame();
//    PrintHelpAuthorized printHelpAuthorized = new PrintHelpAuthorized();
//    PrintHelpUnauthorized printHelpUnauthorized = new PrintHelpUnauthorized();
//    PrintJoinGame printJoinGame = new PrintJoinGame();
//    PrintJoinObserver printJoinObserver = new PrintJoinObserver();
//    PrintListGames printListGames = new PrintListGames();
//    PrintLogin printLogin = new PrintLogin();
//    PrintLogout printLogout = new PrintLogout();
//    PrintQuit printQuit = new PrintQuit();
//    PrintRegister printRegister = new PrintRegister();
//
//    public void helpU() {
//        printHelpUnauthorized.print();
//    }
//
//    public void helpA() {
//        printHelpAuthorized.print();
//    }
//
//    public void quit() {
//        printQuit.print();
//    }
//
//    public String login(Vector<String> vector) {
//        return printLogin.print(vector);
//    }
//
//    public void register(String[] args) {
//        printRegister.print(args);
//    }
//
//    public void logout() {
//        printLogout.print();
//    }
//
//    public void createGame(String[] args) {
//        printCreateGame.print(args);
//    }
//
//    public void listGames() {
//        printListGames.print();
//    }
//
//    public void joinGame(String[] args) {
//        printJoinGame.print(args);
//    }
//
//    public void observeGame(String[] args) {
//        printJoinObserver.print(args);
//    }

