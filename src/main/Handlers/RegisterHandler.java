package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.RegisterRequest;
import result.RegisterResult;
import service.Register;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Stack;

public class RegisterHandler implements Route {
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        RegisterRequest registerRequest = serializer.fromJson(request.body(), RegisterRequest.class);
        Stack<Integer> stack = new Stack<>();
        
        response.type("application/json");
        RegisterResult result;
        
        try {
            result = new Register().register(registerRequest, stack);
        } catch (DataAccessException e) {
            result = new RegisterResult();
            result.setMessage(e.getMessage());
        }
        
        response.status(stack.lastElement());
        return serializer.toJson(result);
    }
}
