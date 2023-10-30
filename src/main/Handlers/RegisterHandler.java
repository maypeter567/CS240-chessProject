package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.RegisterRequest;
import result.RegisterResult;
import service.Register;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        RegisterRequest registerRequest = serializer.fromJson(request.body(), RegisterRequest.class);
        
        response.type("application/json");
        RegisterResult result;
        
        try {
            result = new Register().register(registerRequest, response);
        } catch (DataAccessException e) {
            result = new RegisterResult();
            result.setMessage(e.getMessage());
        }
        return serializer.toJson(result);
    }
}
