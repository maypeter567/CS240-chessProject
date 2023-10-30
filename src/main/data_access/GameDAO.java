package data_access;

import chess.ChessGameImp;
import dataAccess.DataAccessException;
import models.GameMod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameDAO {
    
    static Map<Integer, GameMod> games = new HashMap<>();
    static int i = 0;
    
    /**
     * inserts a new game into the database.
     *
     * @param newGame the game to be added.
     * @throws DataAccessException throws if adding failed.
     */
    public final void insert(GameMod newGame) throws DataAccessException {
        if (games.containsKey(newGame.getGameID())) {
            throw new DataAccessException("This ID is already used by an existing game.");
        } else {
            games.put(newGame.getGameID(), newGame);
        }
    }
    
    /**
     * retrieves a game by gameID
     *
     * @param gameID the unique number of a game to access
     * @return the game to be played
     * @throws DataAccessException throws if the game doesn't exist, or connection error
     */
    public final GameMod find(int gameID) throws DataAccessException {
        return games.getOrDefault(gameID, null);
    }
    
    /**
     * returns all games in a data structure
     *
     * @return a collection of all games
     * @throws DataAccessException if the connection fails
     */
    public final Map<Integer, GameMod> findAll() throws DataAccessException {
        return games;
    }
    
    /**
     * a method for claiming a spot in a game as a player
     *
     * @param username the name of the player to join
     * @throws DataAccessException if the game is already full, no longer exists, or bad connection.
     */
    public final void claimSpot(String username) throws DataAccessException {
        // I am going to have to implement a decently complex method for this...
        throw new DataAccessException("this is not implemented");
    }
    
    /**
     * updates the gameID to another value in the database
     *
     * @param gameID the new value to be added
     * @throws DataAccessException if the game doesn't exist, or the game is unmodifiable
     */
    public final void updateGame(String gameID) throws DataAccessException {
        throw new DataAccessException("this is not implemented");
    }
    
    public int createNewGame(String gameName) throws DataAccessException {
        int gameID = gameIDGenerator();
        insert(new GameMod(gameID, null, null, gameName, new ChessGameImp()));
        return gameID;
    }
    
    private int gameIDGenerator() {
        i++;
        return i;
    }
    
    public void clear() {
        if (!(games == null)) {
            games.clear();
        }
    }
}
