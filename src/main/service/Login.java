package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.UserDAO;
import models.UserMod;
import request.LoginRequest;
import result.LoginResult;

import java.util.Objects;
import java.util.Stack;

public class Login {
    public LoginResult login(LoginRequest request, Stack<Integer> res) throws DataAccessException {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        LoginResult loginResult = new LoginResult();
        
        if (userDAO.containsUser(request.getUsername())) {
            UserMod user = userDAO.findUser(request.getUsername());
            if (Objects.equals(user.getPassword(), request.getPassword())) {
                res.push(200);
                loginResult.setAuthToken(authDAO.getAuthToken(user.getUsername()).getAuthToken());
                loginResult.setUsername(request.getUsername());
                return loginResult;
            } else {
                res.push(401);
                loginResult.setMessage("Error: unauthorized");
                return loginResult;
            }
        } else {
            res.push(401);
            loginResult.setMessage("Error: unauthorized");
            return loginResult;
        }
    }
}