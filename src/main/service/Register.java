package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.UserDAO;
import models.AuthTokenMod;
import models.UserMod;
import request.RegisterRequest;
import result.RegisterResult;
import spark.Response;

public class Register {
    public RegisterResult register(RegisterRequest request, Response res) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        RegisterResult registerResult = new RegisterResult();
        
        if (request.getUsername() == null || request.getPassword() == null || request.getEmail() == null) {
            res.status(400);
            registerResult.setMessage("Error: bad request");
            return registerResult;
        }
        
        if (userDAO.containsUser(request.getUsername())) {
            res.status(403);
            registerResult.setMessage("Error: already taken");
            return registerResult;
        } else {
            userDAO.Insert(new UserMod(request.getUsername(), request.getPassword(), request.getEmail()));
            AuthTokenMod authToken = authDAO.getAuthToken(request.getUsername());
            res.status(200);
            registerResult.setAuthToken(authToken.getAuthToken());
            registerResult.setUsername(request.getUsername());
            return registerResult;
        }
    }
}
