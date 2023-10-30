package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthTokenMod;
import request.ListGamesRequest;
import result.ListGamesResult;
import service.ListGames;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class ListGamesHandler implements Route {
    public Object listGames(Request req, Response res) throws DataAccessException {
        Gson serializer = new Gson();
        ListGamesRequest listGamesRequest = serializer.fromJson(req.body(), ListGamesRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(req.headers("authorization"), null);
        
        res.type("application/json");
        ListGamesResult result;
        try {
            result = new ListGames().listGames(authToken, res);
        } catch (DataAccessException e) {
            result = new ListGamesResult();
            result.setMessage(e.getMessage());
        }
        
        if (Objects.equals(res.type(), "200")) {
            return serializer.toJson(result.getGames());
        } else {
            return serializer.toJson(result.getMessage());
        }
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        ListGamesRequest listGamesRequest = serializer.fromJson(request.body(), ListGamesRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(request.headers("authorization"), null);
        
        response.type("application/json");
        
        ListGamesResult result = new ListGames().listGames(authToken, response);
        return serializer.toJson(result);
    }
}
