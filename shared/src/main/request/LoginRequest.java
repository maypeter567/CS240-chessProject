package request;

public class LoginRequest {
    private String username;
    private String password;
    
    /**
     * this class keeps track of username and password for login requests
     */
    public LoginRequest() {}
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
