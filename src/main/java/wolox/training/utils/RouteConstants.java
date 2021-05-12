package wolox.training.utils;

public final class RouteConstants {

    public static final String BOOK_CONTROLLER_BASE_PATH = "/api/books";
    public static final String PATH_VARIABLE_BOOK_ID = "/{id}";
    public static final String BOOKS_ISBN_PATH = "/isbn";
    public static final String BOOKS_ISBN_ID = "/{isbn}";

    public static final String USERS_CONTROLLER_BASE_PATH = "/api/users";
    public static final String PATH_VARIABLE_USER_ID = "/{id}";
    public static final String USER_BOOKS_PATH = "/books";
    public static final String PATH_VARIABLE_USER_BOOK_ID = "/{bookId}";
    public static final String USER_PASSWORD_PATH = "/password";
    public static final String CLASSPATH_OPENLIBERTYSERVICE_PROPERTIES = "classpath:openlibertyservice.properties";
    public static final String USER_USERNAME_PATH = "/username";

    private RouteConstants() {
    }
}
