package cycling;

public class NameNotRecognisedException extends Exception {
    public NameNotRecognisedException() {
        super("Name not recognized in the system.");
    }
    
    public NameNotRecognisedException(String message) {
        super(message);
    }
} 