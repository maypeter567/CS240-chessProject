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
import java.util.Stack;

public class ListGamesHandler implements Route {
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        ListGamesRequest listGamesRequest = serializer.fromJson(request.body(), ListGamesRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(request.headers("authorization"), null);
        Stack<Integer> stack = new Stack<>();
        
        response.type("application/json");
        
        ListGamesResult result = new ListGames().listGames(authToken, stack);
        
        response.status(stack.lastElement());
        return serializer.toJson(result);
    }
}
