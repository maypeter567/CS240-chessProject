package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthTokenMod;
import request.JoinRequest;
import result.JoinResult;
import service.Join;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        JoinRequest joinRequest = serializer.fromJson(request.body(), JoinRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(request.headers("authorization"), null);
        
        response.type("application/json");
        JoinResult result;
        try {
            result = new Join().join(joinRequest, authToken, response);
        } catch (DataAccessException e) {
            result = new JoinResult();
            result.setMessage(e.getMessage());
        }
        return serializer.toJson(result);
    }
}
