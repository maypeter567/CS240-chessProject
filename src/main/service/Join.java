package service;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import data_access.GameDAO;
import models.AuthTokenMod;
import models.GameMod;
import request.JoinRequest;
import result.JoinResult;

import java.util.Objects;
import java.util.Stack;

public class Join {
    public JoinResult join(JoinRequest request, AuthTokenMod authToken, Stack<Integer> res) throws DataAccessException {
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();
        JoinResult joinResult = new JoinResult();
        
        if (!authDAO.checkVerified(authToken)) {
            joinResult.setMessage("Error: unauthorized");
            res.push(401);
            return joinResult;
        } else {
            authToken = new AuthTokenMod(authToken.getAuthToken(), authDAO.getUsername(authToken));
            request.setAuthToken(authToken);
        }
        
        GameMod game = gameDAO.find(request.getGameID());
        
        if (Objects.equals(game, null)) {
            joinResult.setMessage("Error: bad request");
            res.push(400);
            return joinResult;
        } if (request.getPlayerColor() == null) {
            joinResult.setMessage("you have begun observing the game");
            res.push(200);
        } else if (Objects.equals(request.getPlayerColor(), "WHITE")) {
            if (game.getWhiteUsername() == null) {
                game.setWhiteUsername(authToken.getUsername());
                joinResult.setMessage("you are now the white player");
                gameDAO.updateGame(game.getGameID(), game);
                res.push(200);
            } else {
                joinResult.setMessage("Error: player colour already taken");
                res.push(403);
                return joinResult;
            }
        } else {
            if (game.getBlackUsername() == null) {
                game.setBlackUsername(authToken.getUsername());
                joinResult.setMessage("you are now the black player");
                gameDAO.updateGame(game.getGameID(), game);
                res.push(200);
                return joinResult;
            } else {
                joinResult.setMessage("Error: player colour already taken");
                res.push(403);
                return joinResult;
            }
        }
        return joinResult;
    }
}
