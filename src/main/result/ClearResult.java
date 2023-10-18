package result;

public class ClearResult {
    private String message;
    
    /**
     * only a message can be returned for this class
     */
    public ClearResult() {}
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
