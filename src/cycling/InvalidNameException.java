package cycling;

public class InvalidNameException extends Exception {
    public InvalidNameException() {
        super("Name is invalid (null, empty, or too long).");
    }
    
    public InvalidNameException(String message) {
        super(message);
    }
} 