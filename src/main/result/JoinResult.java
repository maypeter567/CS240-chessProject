package result;

public class JoinResult {
    private String message;
    
    /**
     * join only expects message values
     */
    public JoinResult() {}
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
