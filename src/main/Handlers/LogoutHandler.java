package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthTokenMod;
import request.LogoutRequest;
import result.LogoutResult;
import service.Logout;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        LogoutRequest logoutRequest = serializer.fromJson(request.body(), LogoutRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(request.headers("authorization"), null);
        
        response.type("application/json");
        LogoutResult result;
        
        try {
            result = new Logout().logout(logoutRequest, authToken, response);
        } catch (DataAccessException e) {
            result = new LogoutResult();
            result.setMessage(e.getMessage());
        }
        return serializer.toJson(result);
    }
}
