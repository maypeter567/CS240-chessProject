package data_access;

import dataAccess.DataAccessException;
import models.AuthTokenMod;

public class AuthDAO {
    
    /**
     * returns an authentication token from the database.
     *
     * @param username the name attached to the authToken.
     * @return a AuthToken for future communications with the database.
     * @throws DataAccessException if the authorization request is denied
     */
    public final AuthTokenMod getAuthToken(String username) throws DataAccessException { return null; }
    
    /**
     * checks to see if the token is still valid.
     *
     * @param token current authentication token.
     * @return returns whether the token is still valid.
     * @throws DataAccessException if token doesn't exist in database
     */
    public final boolean checkVerified(AuthTokenMod token) throws DataAccessException { return false; }
}
