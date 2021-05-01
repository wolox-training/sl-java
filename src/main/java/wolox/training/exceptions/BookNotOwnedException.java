package wolox.training.exceptions;

public class BookNotOwnedException extends RuntimeException{

    public BookNotOwnedException() {
        super("user does not own the book");
    }
}
