package cycling;

public class IllegalNameException extends Exception {
    public IllegalNameException() {
        super("Name already exists in the system.");
    }
    
    public IllegalNameException(String message) {
        super(message);
    }
} 