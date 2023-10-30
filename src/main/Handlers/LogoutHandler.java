package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthTokenMod;
import request.LoginRequest;
import request.LogoutRequest;
import result.LoginResult;
import result.LogoutResult;
import service.Login;
import service.Logout;
import service.Register;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class LogoutHandler implements Route {
    public Object logout(Request req, Response res) throws DataAccessException {
        Gson serializer = new Gson();
        LogoutRequest logoutRequest = serializer.fromJson(req.body(), LogoutRequest.class);
        AuthTokenMod authToken = serializer.fromJson(req.headers("authentication"), AuthTokenMod.class); // THIS IS WHERE I WAS WORKING.
        
        res.type("application/json");
        
        LogoutResult result = new Logout().logout(logoutRequest, authToken, res);
        if (Objects.equals(res.type(), "200:")) {
            return serializer.toJson(result);
        } else {
            return serializer.toJson(result.getMessage());
        }
    }
    
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
