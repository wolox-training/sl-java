package wolox.training.exceptions;

public class IdMismatchException extends RuntimeException {

    public IdMismatchException() {
        super("ID does not match");
    }
}
