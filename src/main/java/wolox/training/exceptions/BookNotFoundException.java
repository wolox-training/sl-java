package wolox.training.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("book does not exits");
    }
}
