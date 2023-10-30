package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.GameDAO;
import models.AuthTokenMod;
import models.GameMod;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.CreateGame;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class CreateGameHandler implements Route {
    public Object createGame(Request req, Response res) throws DataAccessException {
        Gson serializer = new Gson();
        CreateGameRequest createGameRequest = serializer.fromJson(req.body(), CreateGameRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(req.headers("authorization"), null);
        
        res.type("application/json");
        
        CreateGameResult result;
        try {
            result = new CreateGame().createGame(createGameRequest, authToken, res);
        } catch (DataAccessException e) {
            result = new CreateGameResult();
            result.setMessage(e.getMessage());
        }
        
        return serializer.toJson(result);
    }
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return createGame(request, response);
    }
}


//        Gson serializer = new Gson();
//        CreateGameRequest gameRequest = serializer.fromJson(req.body(), CreateGameRequest.class);

//        var test = serializer.toJson(gameRequest);
