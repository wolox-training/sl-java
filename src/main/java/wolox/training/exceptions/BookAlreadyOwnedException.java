package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException {

    public BookAlreadyOwnedException() {
        super("The book are already owned by the user");
    }
}
