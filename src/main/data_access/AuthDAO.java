package data_access;

import dataAccess.DataAccessException;
import models.AuthTokenMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AuthDAO {
    
    static Map<String, String> tokens = new HashMap<>();
    
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
        if (token == null) {
            return false;
        }
        return tokens.containsKey(token.getAuthToken());
    }
    
    public void logout(AuthTokenMod token) throws DataAccessException {
        if (token == null) {
            throw new DataAccessException("The authorization token wasn't passed into the function");
        } else {
            tokens.remove(token.getAuthToken());
        }
    }
    
    public String getUsername(AuthTokenMod token) throws DataAccessException {
        if (token == null) {
            throw new DataAccessException("The authorization token wasn't passed into the function");
        } else if (checkVerified(token)) {
            return tokens.get(token.getAuthToken());
        } else {
            return null;
        }
    }
    
    private String AuthTokenGenerator() {
        return UUID.randomUUID().toString();
    }
    
    public void clear() {
        if (!(tokens == null)) {
            tokens.clear();
        }
    }
}
