package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.RegisterRequest;
import result.RegisterResult;
import service.Register;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class RegisterHandler implements Route {
    
    public Object register(Request req, Response res) throws DataAccessException {
        Gson serializer = new Gson();
        RegisterRequest registerRequest = serializer.fromJson(req.body(), RegisterRequest.class);

        res.type("application/json");

        RegisterResult result = new Register().register(registerRequest, res);
        if (Objects.equals(res.type(), "200")) {
            return serializer.toJson(result);
        } else {
            return serializer.toJson(result);
        }
    }
    
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
