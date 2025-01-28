package cycling;

public class InvalidLocationException extends Exception {
    public InvalidLocationException() {
        super("Location is out of bounds of the stage length.");
    }
    
    public InvalidLocationException(String message) {
        super(message);
    }
} 