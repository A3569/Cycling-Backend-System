package cycling;

public class DuplicatedResultException extends Exception {
    public DuplicatedResultException() {
        super("Rider already has a result for this stage.");
    }
    
    public DuplicatedResultException(String message) {
        super(message);
    }
} 