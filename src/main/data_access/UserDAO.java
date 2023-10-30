package data_access;

import dataAccess.DataAccessException;
import models.UserMod;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    
    private static Map<String, UserMod> users = new HashMap<>();
    
    /**
     * this function clears the local and database list of users for testing purposes.
     *
     * @throws DataAccessException the function might fail to access the database.
     */
    public void clear() throws DataAccessException {
        if (!(users == null)) {
            users.clear();
        }
    }
    
    /**
     * this function is used to add new users.
     *
     * @param myUserMod the user to be added.
     * @throws DataAccessException when insert fails to add myUserMod.
     */
    public void Insert(UserMod myUserMod) throws DataAccessException {
        if (users.containsKey(myUserMod.getUsername())) {
            throw new DataAccessException("this user already exists");
        } else {
            users.put(myUserMod.getUsername(), myUserMod);
        }
    }
    
    /**
     * deletes a specified user from the database
     *
     * @param myUserMod the user to be deleted.
     */
    public void remove(UserMod myUserMod) {
        users.remove(myUserMod.getUsername());
    }
    public UserMod findUser(String username) throws DataAccessException {
        if (users.containsKey(username)) {
            return users.get(username);
        } else {
            throw new DataAccessException("this user doesn't exist");
        }
    }
    
    /**
     * returns a Collection of User's to be worked with for testing.
     *
     * @return a collection of the users in the database.
     */
    public Map<String, UserMod> findAllUser() throws DataAccessException {
        return users;
    }
    
    public boolean containsUser(String username) {
        return users.containsKey(username);
    }
}
