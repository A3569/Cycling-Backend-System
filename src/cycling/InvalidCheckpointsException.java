package cycling;

public class InvalidCheckpointsException extends Exception {
    public InvalidCheckpointsException() {
        super("Invalid number of checkpoints provided.");
    }
    
    public InvalidCheckpointsException(String message) {
        super(message);
    }
} 