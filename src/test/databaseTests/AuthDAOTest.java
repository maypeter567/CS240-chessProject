package databaseTests;

import dataAccess.DataAccessException;
import data_access.AuthDAO;
import models.AuthTokenMod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;
import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {
    
    private final AuthDAO authDAO = new AuthDAO();
    
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }
    
    @Test
    void getAuthTokenPass() {
        try (var conn = getConnection()) {
            
            authDAO.clear();
            var authToken = authDAO.getAuthToken("test1");
            
            conn.setCatalog("chess");
            
            var testAuthToken = """
                SELECT authTokenValue, username FROM authTokens WHERE authTokenValue=?
                """;
            
            try(var preparedStatement = conn.prepareStatement(testAuthToken)) {
                preparedStatement.setString(1, authToken.getAuthToken());
                var rs = preparedStatement.executeQuery();
                
                if (rs.next()) {
                    var id = rs.getString("authTokenValue");
                    var name = rs.getString("username");
                    
                    Assertions.assertEquals(id, authToken.getAuthToken());
                    
                } else {
                    Assertions.fail("the authToken was never made");
                }
            }
        } catch (SQLException | DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void getAuthTokenFail() {
        try (var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            for (int i = 0; i < 15; i++) {
                authDAO.clear();
                var authToken = authDAO.getAuthToken("test1");
                
                conn.setCatalog("chess");
                
                var testAuthToken = """
                SELECT authTokenValue, username FROM authTokens WHERE authTokenValue=?
                """;
                
                try(var preparedStatement = conn.prepareStatement(testAuthToken)) {
                    preparedStatement.setString(1, authToken.getAuthToken());
                    var rs = preparedStatement.executeQuery();
                    
                    if (rs.next()) {
                        var id = rs.getString("authTokenValue");
                        var name = rs.getString("username");
                        
                        Assertions.assertEquals(id, authToken.getAuthToken());
                        
                    } else {
                        Assertions.fail("the authToken was never made");
                    }
                }
            }
            
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void checkVerifiedPass() {
        try (var conn = getConnection()) {
            
            authDAO.clear();
            var authToken = authDAO.getAuthToken("test1");
            
            conn.setCatalog("chess");
            
            assertTrue(authDAO.checkVerified(authToken));
        } catch (SQLException | DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void checkVerifiedFail() {
        try (var conn = getConnection()) {
            
            authDAO.clear();
            var authToken = authDAO.getAuthToken("test1");
            
            authDAO.clear();
            
            conn.setCatalog("chess");
            
            assertFalse(authDAO.checkVerified(authToken));
        } catch (SQLException | DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void logoutPass() {
        try (var conn = getConnection()) {
            
            authDAO.clear();
            var authToken = authDAO.getAuthToken("test1");
            
            conn.setCatalog("chess");
            
            authDAO.logout(authToken);
            
            assertFalse(authDAO.checkVerified(authToken));
        } catch (SQLException | DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void logoutFail() {
        try (var conn = getConnection()) {
            
            authDAO.clear();
            var authToken = authDAO.getAuthToken("test1");
            
            conn.setCatalog("chess");
            
            assertThrows(
                    DataAccessException.class, // replace with the expected type of the exception
                    () -> {
                        authDAO.logout(null);
                    }
            );
            
        } catch (SQLException | DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void getUsernamePass() {
        try (var conn = getConnection()) {
            
            authDAO.clear();
            var authToken = authDAO.getAuthToken("test1");
            
            conn.setCatalog("chess");
            
            Assertions.assertEquals(authToken.getUsername(), authDAO.getUsername(authToken));
        } catch (SQLException | DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void getUsernameFail() {
        try (var conn = getConnection()) {
            
            authDAO.clear();
            var authToken = authDAO.getAuthToken("test1");
            var authTokenTest = new AuthTokenMod("3020", "bobby");
            
            conn.setCatalog("chess");
            
            assertNull(authDAO.getUsername(authTokenTest));
        } catch (SQLException | DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void clear() {
        try {
            var authToken = authDAO.getAuthToken("test1");
            
            authDAO.clear();
            
            assertNull(authDAO.getUsername(authToken));
            
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}