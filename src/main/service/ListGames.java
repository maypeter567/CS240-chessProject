package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.GameDAO;
import models.AuthTokenMod;
import models.GameMod;
import result.ListGamesResult;
import spark.Response;

import java.util.Vector;

public class ListGames {
    public ListGamesResult listGames(AuthTokenMod authToken, Response res) throws DataAccessException {
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();
        ListGamesResult listGamesResult = new ListGamesResult();
        
        if (!authDAO.checkVerified(authToken)) {
            listGamesResult.setMessage("Error: unauthorized");
            res.status(401);
            return listGamesResult;
        }
        
        var map = gameDAO.findAll();
        Vector<GameMod> vector = new Vector<>(map.values());
        
        res.status(200);
        listGamesResult.setGames(vector);
        return listGamesResult;
    }
}
