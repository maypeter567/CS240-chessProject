package data_access;

import dataAccess.DataAccessException;
import models.AuthTokenMod;

import java.util.Map;
import java.util.Objects;

public class AuthDAO {
    
    int i = 0;
    
    Map<String, String> tokens;
    
    /**
     * returns an authentication token from the database.
     *
     * @param username the name attached to the authToken.
     * @return a AuthToken for future communications with the database.
     * @throws DataAccessException if the authorization request is denied
     */
    public final AuthTokenMod getAuthToken(String username) throws DataAccessException {
        String AuthToken = AuthTokenGenerator();
        tokens.put(AuthToken, username);
        return new AuthTokenMod(AuthToken, username);
    }
    
    /**
     * checks to see if the token is still valid.
     *
     * @param token current authentication token.
     * @return returns whether the token is still valid.
     * @throws DataAccessException if token doesn't exist in database
     */
    public final boolean checkVerified(AuthTokenMod token) throws DataAccessException {
        return Objects.equals(tokens.get(token.getAuthToken()), token.getUsername());
    }
    
    private String AuthTokenGenerator() {
        String toReturn = String.valueOf(i);
        i++;
        return toReturn;
    }
}
