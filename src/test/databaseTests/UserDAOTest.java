package databaseTests;

import dataAccess.DataAccessException;
import data_access.UserDAO;
import models.UserMod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    
    UserDAO userDAO = new UserDAO();
    
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }
    
    @BeforeEach
    public void setup() {
        try {
            userDAO.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void clear() {
        try {
            userDAO.Insert(new UserMod("test", "password", "email"));
            
            userDAO.clear();
            
            Assertions.assertFalse(userDAO.containsUser("test"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void insertPass() {
        try (var conn = getConnection()) {
            userDAO.Insert(new UserMod("test2", "password", "email"));
            userDAO.Insert(new UserMod("test1", "password", "email"));
            
            conn.setCatalog("chess");
            
            var getInserts1 = """
                    SELECT userMod FROM users WHERE name=?
                    """;
            
            var getInserts2 = """
                    SELECT userMod FROM users WHERE name=?
                    """;
            
            try (var request1 = conn.prepareStatement(getInserts1)) {
                request1.setString(1, "test1");
                var rs = request1.executeQuery();
                
                if (rs.next()) {
                    Assertions.assertNotEquals(rs.getString("userMod"), null);
                } else {
                    Assertions.fail();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
            
            try (var request2 = conn.prepareStatement(getInserts2)) {
                request2.setString(1, "test2");
                var rs = request2.executeQuery();
                
                if (rs.next()) {
                    Assertions.assertNotEquals(rs.getString("userMod"), null);
                } else {
                    Assertions.fail();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
            
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void insertFail() {
        try {
            userDAO.Insert(new UserMod("test1", "password", "email"));
            
            userDAO.clear();
            
            Assertions.assertFalse(userDAO.containsUser("test1"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void findUserPass() {
        try {
            var user = new UserMod("test1", "password", "email");
            userDAO.Insert(user);
            
            Assertions.assertEquals(userDAO.findUser("test1").getUsername(), user.getUsername());
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void findUserFail() {
        try {
            var user = new UserMod("test1", "password", "email");
            userDAO.Insert(user);
            assertThrows(
                    DataAccessException.class, // replace with the expected type of the exception
                    () -> {
                        userDAO.findUser("test2");
                    }
            );
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Test
    void findAllUserPass() {
        try {
            userDAO.Insert(new UserMod("test1", "password", "email"));
            userDAO.Insert(new UserMod("test2", "password", "email"));
            userDAO.Insert(new UserMod("test3", "password", "email"));
            userDAO.Insert(new UserMod("test4", "password", "email"));
            userDAO.Insert(new UserMod("test5", "password", "email"));
            userDAO.Insert(new UserMod("test6", "password", "email"));
            
            Map<String, UserMod> map = userDAO.findAllUser();
            
            Assertions.assertTrue(map.containsKey("test1"));
            Assertions.assertTrue(map.containsKey("test2"));
            Assertions.assertTrue(map.containsKey("test3"));
            Assertions.assertTrue(map.containsKey("test4"));
            Assertions.assertTrue(map.containsKey("test5"));
            Assertions.assertTrue(map.containsKey("test6"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void findAllUserFail() {
        try {
            userDAO.Insert(new UserMod("test1", "password", "email"));
            userDAO.Insert(new UserMod("test2", "password", "email"));
            userDAO.Insert(new UserMod("test3", "password", "email"));
            userDAO.Insert(new UserMod("test4", "password", "email"));
            userDAO.Insert(new UserMod("test5", "password", "email"));
            userDAO.Insert(new UserMod("test6", "password", "email"));
            
            Map<String, UserMod> map = userDAO.findAllUser();
            
            Assertions.assertFalse(map.containsKey("test20"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    void containsUserPass() {
        try {
            userDAO.Insert(new UserMod("test1", "password", "email"));
            userDAO.Insert(new UserMod("test2", "password", "email"));
            userDAO.Insert(new UserMod("test3", "password", "email"));
            userDAO.Insert(new UserMod("test4", "password", "email"));
            userDAO.Insert(new UserMod("test5", "password", "email"));
            userDAO.Insert(new UserMod("test6", "password", "email"));
            
            Assertions.assertTrue(userDAO.containsUser("test1"));
            Assertions.assertTrue(userDAO.containsUser("test2"));
            Assertions.assertTrue(userDAO.containsUser("test3"));
            Assertions.assertTrue(userDAO.containsUser("test4"));
            Assertions.assertTrue(userDAO.containsUser("test5"));
            Assertions.assertTrue(userDAO.containsUser("test6"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}