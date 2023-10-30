package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.GameDAO;
import models.AuthTokenMod;
import models.GameMod;
import request.CreateGameRequest;
import result.CreateGameResult;
import spark.Response;

public class CreateGame {
    public CreateGameResult createGame(CreateGameRequest request, AuthTokenMod authToken, Response res) throws DataAccessException { // accesses database
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();
        CreateGameResult createGameResult = new CreateGameResult();
        
        if (!authDAO.checkVerified(authToken)) {
            createGameResult.setMessage("Error: unauthorized");
            res.status(401);
            return createGameResult;
        }
        
        int gameID = gameDAO.createNewGame(request.getGameName());
        GameMod newGame = gameDAO.find(gameID);
        
        if (newGame == null) {
            createGameResult.setMessage("Error: game failed to be made");
            res.status(400);
        } else {
            createGameResult.setGameID(gameID);
            res.status(200);
        }
        return createGameResult;
    }
}
