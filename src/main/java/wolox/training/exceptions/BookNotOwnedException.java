package wolox.training.exceptions;

public class BookNotOwnedException extends RuntimeException {

    public BookNotOwnedException(String msg) {
        super(msg);
    }
}
