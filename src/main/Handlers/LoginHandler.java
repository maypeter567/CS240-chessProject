package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.LoginRequest;
import result.LoginResult;
import service.Login;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Stack;

public class LoginHandler implements Route {
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        LoginRequest loginRequest = serializer.fromJson(request.body(), LoginRequest.class);
        Stack<Integer> stack = new Stack<>();
        
        response.type("application/json");
        LoginResult result;
        
        try {
            result = new Login().login(loginRequest, stack);
        } catch (DataAccessException e) {
            result = new LoginResult();
            result.setMessage(e.getMessage());
        }
        
        response.status(stack.lastElement());
        return serializer.toJson(result);
    }
}
