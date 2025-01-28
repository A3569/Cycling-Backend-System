package cycling;

public class InvalidStageTypeException extends Exception {
    public InvalidStageTypeException() {
        super("Invalid stage type for this operation.");
    }
    
    public InvalidStageTypeException(String message) {
        super(message);
    }
} 