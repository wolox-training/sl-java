package wolox.training.utils;

public final class MessageError {

    public static final String BOOK_ID_MISMATCH_MSG = "book ID does not match";
    public static final String BOOK_NOT_FOUND_MSG = "book not found";
    public static final String BOOK_ALREADY_OWNED_MSG = "The book are already owned by the user";
    public static final String BOOK_NOT_OWNED_MSG = "user does not own the book";
    public static final String USER_ID_MISMATCH_MSG = "user ID does not match";
    public static final String USER_NOT_FOUND_MSG = "user not found";
    public static final String CHECK_NOT_NULL_MESSAGE = "Please check the Object supplied, its null!";

    private MessageError() {
    }
}
