package data_access;

import chess.ChessGameImp;
import dataAccess.DataAccessException;

import java.util.Collection;

public class GameDAO {
    
    /**
     * inserts a new game into the database.
     *
     * @param newGame the game to be added.
     * @throws DataAccessException throws if adding failed.
     */
    public final void insert(ChessGameImp newGame) throws DataAccessException {}
    
    /**
     * retrieves a game by gameID
     *
     * @param gameID the unique number of a game to access
     * @return the game to be played
     * @throws DataAccessException throws if the game doesn't exist, or connection error
     */
    public final ChessGameImp find(String gameID) throws DataAccessException { return null; }
    
    /**
     * returns all games in a data structure of your choice
     *
     * @return a collection of all games
     * @throws DataAccessException if the connection fails
     */
    public final Collection<ChessGameImp> findAll() throws DataAccessException { return null; }
    
    /**
     * a method for claiming a spot in a game as a player
     *
     * @param username the name of the player to join
     * @throws DataAccessException if the game is already full, no longer exists, or bad connection.
     */
    public final void claimSpot(String username) throws DataAccessException {}
    
    /**
     * updates the gameID to another value in the database
     *
     * @param gameID the new value to be added
     * @throws DataAccessException if the game doesn't exist, or the game is unmodifiable
     */
    public final void updateGame(String gameID) throws DataAccessException {}
    
    
}
