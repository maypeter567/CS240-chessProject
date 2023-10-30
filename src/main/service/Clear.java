package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.GameDAO;
import data_access.UserDAO;
import result.ClearResult;

public class Clear {
    public ClearResult clear() throws DataAccessException {
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();
        UserDAO userDAO = new UserDAO();
        
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
        
        return new ClearResult();
    }
}
