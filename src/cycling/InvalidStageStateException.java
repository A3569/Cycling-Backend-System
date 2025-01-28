package cycling;

public class InvalidStageStateException extends Exception {
    public InvalidStageStateException() {
        super("Stage is in an invalid state for this operation.");
    }
    
    public InvalidStageStateException(String message) {
        super(message);
    }
} 