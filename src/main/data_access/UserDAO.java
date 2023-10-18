package data_access;

import dataAccess.DataAccessException;
import models.UserMod;

import java.util.Collection;

public class UserDAO {
    /**
     * this function clears the local and database list of users for testing purposes.
     *
     * @throws DataAccessException the function might fail to access the database.
     */
    public void clear() throws DataAccessException {}
    
    /**
     * this function is used to add new users.
     *
     * @param myUserMod the user to be added.
     * @throws DataAccessException when insert fails to add myUserMod.
     */
    public void Insert(UserMod myUserMod) throws DataAccessException {}
    
    /**
     * deletes a specified user from the database
     *
     * @param myUserMod the user to be deleted.
     */
    public void remove(UserMod myUserMod) {}
    public UserMod findUser(String username) throws DataAccessException { return null; }
    
    /**
     * returns a Collection of User's to be worked with for testing.
     *
     * @return a collection of the users in the database.
     */
    public Collection<UserMod> findAllUser() throws DataAccessException { return null; }
}
