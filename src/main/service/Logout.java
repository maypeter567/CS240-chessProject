package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import models.AuthTokenMod;
import request.LogoutRequest;
import result.LogoutResult;
import spark.Response;

import java.util.Stack;

public class Logout {
    public LogoutResult logout(LogoutRequest request, AuthTokenMod authToken, Stack<Integer> res) throws DataAccessException {
        AuthDAO authDAO = new AuthDAO();
        LogoutResult logoutResult = new LogoutResult();
        
        if (!authDAO.checkVerified(authToken)) {
            logoutResult.setMessage("Error: unauthorized");
            res.push(401);
            return logoutResult;
        }
        
        authDAO.logout(authToken);
        if (authDAO.checkVerified(authToken)) {
            res.push(401);
            logoutResult.setMessage("Error: logout failed");
            return logoutResult;
        } else {
            res.push(200);
            return logoutResult;
        }
    }
}
