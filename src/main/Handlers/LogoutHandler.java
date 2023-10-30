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

import java.util.Stack;

public class LogoutHandler implements Route {
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        LogoutRequest logoutRequest = serializer.fromJson(request.body(), LogoutRequest.class);
        AuthTokenMod authToken = new AuthTokenMod(request.headers("authorization"), null);
        Stack<Integer> stack = new Stack<>();
        
        response.type("application/json");
        LogoutResult result;
        
        try {
            result = new Logout().logout(logoutRequest, authToken, stack);
        } catch (DataAccessException e) {
            result = new LogoutResult();
            result.setMessage(e.getMessage());
        }
        
        response.status(stack.lastElement());
        return serializer.toJson(result);
    }
}
