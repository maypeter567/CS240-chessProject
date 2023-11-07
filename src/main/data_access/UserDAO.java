package data_access;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import models.AuthTokenMod;
import models.UserMod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    
    private final static Map<String, UserMod> users = new HashMap<>();
    
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysqlNBZIEN12!");
    }
    
    /**
     * this function clears the local and database list of users for testing purposes.
     *
     * @throws DataAccessException the function might fail to access the database.
     */
    public void clear() throws DataAccessException {
        try (var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var checkAuthToken = """
                                DROP TABLE IF EXISTS users
                        """;
//            System.out.println(createNewAuthToken);
            
            try (var preparedStatement = conn.prepareStatement(checkAuthToken)) {
                preparedStatement.executeUpdate();
            }
            
            var createUserTable = """
                CREATE TABLE IF NOT EXISTS users (
                name VARCHAR(225) NOT NULL,
                userMod TEXT NOT NULL,
                PRIMARY KEY (name)
            )""";
            
            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
            
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    
    /**
     * this function is used to add new users.
     *
     * @param myUserMod the user to be added.
     * @throws DataAccessException when insert fails to add myUserMod.
     */
    public void Insert(UserMod myUserMod) throws DataAccessException {
        try(var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var createNewUser = """
                INSERT INTO users (name, userMod) VALUES (?, ?)
                """;
//            System.out.println(createNewAuthToken);
            
            var checkForUser = """
                SELECT name, userMod FROM users WHERE name=?
                """;
            
            try(var userExists = conn.prepareStatement(checkForUser)) {
                userExists.setString(1, myUserMod.getUsername());
                var temp = userExists.executeQuery();
                if (temp.next()) {
                    throw new DataAccessException("this user already exists");
                } else {
                    
                    try (var preparedStatement = conn.prepareStatement(createNewUser)) {
                        preparedStatement.setString(1, myUserMod.getUsername());
                        Gson tempGson = new Gson();
                        String serializedMUM = tempGson.toJson(myUserMod);
                        preparedStatement.setString(2, serializedMUM);
                        preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    
    public UserMod findUser(String username) throws DataAccessException {
        try(var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var findUser = """
                SELECT name, userMod FROM users WHERE name=?
                """;
//            System.out.println(createNewAuthToken);
            
            try(var preparedStatement = conn.prepareStatement(findUser)) {
                preparedStatement.setString(1, username);
                var rs = preparedStatement.executeQuery();
                
                if (rs.next()) {
                    var name = rs.getString("name");
                    var userModString = rs.getString("userMod");
                    Gson gson = new Gson();
                    return gson.fromJson(userModString, UserMod.class);
                } else {
                    throw new DataAccessException("this user doesn't exist");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    
    /**
     * returns a Collection of User's to be worked with for testing.
     *
     * @return a collection of the users in the database.
     */
    public Map<String, UserMod> findAllUser() throws DataAccessException {
        Map<String, UserMod> newMap = new HashMap<>();
        try(var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var findUser = """
                SELECT name, userMod FROM users
                """;
//            System.out.println(createNewAuthToken);
            
            try(var preparedStatement = conn.prepareStatement(findUser)) {
                var rs = preparedStatement.executeQuery();
                Gson gson = new Gson();
                
                while (rs.next()) {
                    newMap.put(rs.getString("name"), gson.fromJson(rs.getString("userMod"), UserMod.class));
                }
                
                return newMap;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    
    public boolean containsUser(String username) throws DataAccessException {
        try(var conn = getConnection()) {
            
            conn.setCatalog("chess");
            
            var findUser = """
                SELECT name, userMod FROM users WHERE name=?
                """;
//            System.out.println(createNewAuthToken);
            
            try(var preparedStatement = conn.prepareStatement(findUser)) {
                preparedStatement.setString(1, username);
                var rs = preparedStatement.executeQuery();
                
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
