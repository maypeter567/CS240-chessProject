package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.GameDAO;
import models.AuthTokenMod;
import models.GameMod;
import result.ListGamesResult;

import java.util.Stack;
import java.util.Vector;

public class ListGames {
    public ListGamesResult listGames(AuthTokenMod authToken, Stack<Integer> res) throws DataAccessException {
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();
        ListGamesResult listGamesResult = new ListGamesResult();
        
        if (!authDAO.checkVerified(authToken)) {
            listGamesResult.setMessage("Error: unauthorized");
            res.push(401);
            return listGamesResult;
        }
        
        var map = gameDAO.findAll();
        Vector<GameMod> vector = new Vector<>(map.values());
        
        res.push(200);
        listGamesResult.setGames(vector);
        return listGamesResult;
    }
}
