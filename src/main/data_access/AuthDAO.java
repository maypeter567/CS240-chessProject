package data_access;

import dataAccess.DataAccessException;
import models.AuthTokenMod;

import java.sql.*;

public class AuthDAO {
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }
    
    /**
     * returns an authentication token from the database.
     *
     * @param username the name attached to the authToken.
     * @return a AuthToken for future communications with the database.
     * @throws DataAccessException if the authorization request is denied
     */
    public final AuthTokenMod getAuthToken(String username) throws DataAccessException {
        try(var conn = getConnection()) {
        
            conn.setCatalog("chess");
            
            var createNewAuthToken = """
                INSERT INTO authTokens (username) VALUES (?)
                """;
//            System.out.println(createNewAuthToken);
            
            try(var preparedStatement = conn.prepareStatement(createNewAuthToken, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();
                
                var authToken = preparedStatement.getGeneratedKeys();
                if (authToken.next()) {
                    return new AuthTokenMod(authToken.getString(1), username);
                } else {
                    throw new DataAccessException("authorization was not granted by database");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    
    /**
     * checks to see if the token is still valid.
     *
     * @param token current authentication token.
     * @return returns whether the token is still valid.
     * @throws DataAccessException if token doesn't exist in database
     */
    public final boolean checkVerified(AuthTokenMod token) throws DataAccessException {
        try(var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var checkAuthToken = """
                SELECT authTokenValue, username FROM authTokens WHERE authTokenValue=?
                """;
//            System.out.println(createNewAuthToken);
            
            try(var preparedStatement = conn.prepareStatement(checkAuthToken)) {
                preparedStatement.setString(1, token.getAuthToken());
                var rs = preparedStatement.executeQuery();
                
                if (rs.next()) {
                    var id = rs.getString("authTokenValue");
                    var name = rs.getString("username");
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                return false;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    
    public void logout(AuthTokenMod token) throws DataAccessException {
        if (token == null) {
            throw new DataAccessException("The authorization token wasn't passed into the function");
        } else {
            try (var conn = getConnection()) {
                
                conn.setCatalog("chess");
                
                var deleteAuthToken = """
                        DELETE FROM authTokens WHERE authTokenValue=?
                        """;
//            System.out.println(createNewAuthToken);
                
                try (var preparedStatement = conn.prepareStatement(deleteAuthToken)) {
                    preparedStatement.setString(1, token.getAuthToken());
                    var rs = preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }
    }
    
    public String getUsername(AuthTokenMod token) throws DataAccessException {
        if (token == null) {
            throw new DataAccessException("The authorization token wasn't passed into the function");
        } else {
            try (var conn = getConnection()) {
                
                conn.setCatalog("chess");
                
                var checkAuthToken = """
                                
                                SELECT authTokenValue, username FROM authTokens WHERE authTokenValue=?
                        """;
//            System.out.println(createNewAuthToken);
                
                try (var preparedStatement = conn.prepareStatement(checkAuthToken)) {
                    preparedStatement.setString(1, token.getAuthToken());
                    var rs = preparedStatement.executeQuery();
                    
                    if (rs.next()) {
                        var id = rs.getString("authTokenValue");
                        var name = rs.getString("username");
                        return name;
                    } else {
                        return null;
                    }
                }
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }
    }
    
    public void clear() throws DataAccessException {
        try (var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var checkAuthToken = """
                                DROP TABLE IF EXISTS authTokens
                        """;
//            System.out.println(createNewAuthToken);
            
            try (var preparedStatement = conn.prepareStatement(checkAuthToken)) {
                preparedStatement.executeUpdate();
            }
            
            var createAuthTable = """
                    CREATE TABLE IF NOT EXISTS authTokens (
                    authTokenValue int NOT NULL AUTO_INCREMENT,
                    username VARCHAR(225) NOT NULL,
                    PRIMARY KEY (authTokenValue)
                    )""";
            
            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
