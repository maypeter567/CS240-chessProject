package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.LoginRequest;
import result.LoginResult;
import service.Login;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        LoginRequest loginRequest = serializer.fromJson(request.body(), LoginRequest.class);
        
        response.type("application/json");
        LoginResult result;
        
        try {
            result = new Login().login(loginRequest, response);
        } catch (DataAccessException e) {
            result = new LoginResult();
            result.setMessage(e.getMessage());
        }
        return serializer.toJson(result);
    }
}
