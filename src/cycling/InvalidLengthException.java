package cycling;

public class InvalidLengthException extends Exception {
    public InvalidLengthException() {
        super("Stage length is invalid.");
    }
    
    public InvalidLengthException(String message) {
        super(message);
    }
} 