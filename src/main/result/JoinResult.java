package result;

public class JoinResult {
    private String Message;
    
    /**
     * join only expects message values
     */
    public JoinResult() {}
    
    public String getMessage() {
        return Message;
    }
    
    public void setMessage(String message) {
        Message = message;
    }
}
