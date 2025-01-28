package cycling;

public class IDNotRecognisedException extends Exception {
    public IDNotRecognisedException() {
        super("ID not recognized in the system.");
    }
    
    public IDNotRecognisedException(String message) {
        super(message);
    }
}