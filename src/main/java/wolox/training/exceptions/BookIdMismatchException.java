package wolox.training.exceptions;

public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException() {
        super("book ID does not match");
    }
}
