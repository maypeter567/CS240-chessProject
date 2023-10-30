package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthTokenMod;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.CreateGame;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Stack;

public class CreateGameHandler implements Route {
    public Object createGame(Request req, Response res) throws DataAccessException {
        Gson serializer = new Gson();
        CreateGameRequest createGameRequest = serializer.fromJson(req.body(), CreateGameRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(req.headers("authorization"), null);
        
        res.type("application/json");
        
        Stack<Integer> stack = new Stack<Integer>();
        
        CreateGameResult result;
        try {
            result = new CreateGame().createGame(createGameRequest, authToken, stack);
        } catch (DataAccessException e) {
            result = new CreateGameResult();
            result.setMessage(e.getMessage());
        }
        res.status(stack.lastElement());
        return serializer.toJson(result);
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return createGame(request, response);
    }
}