package wolox.training.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("the book does not exits");
    }
}
