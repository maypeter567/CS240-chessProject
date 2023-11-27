package models;

public class UserMod {
    private final String username;
    private final String password;
    private final String email;
    
    /**
     *
     * @param username this is the username of the player
     * @param password the proposed password for the player
     * @param email the email for the user
     */
    public UserMod(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
}
