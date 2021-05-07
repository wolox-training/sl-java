package wolox.training.test.util;

import java.util.Arrays;
import java.util.List;
import wolox.training.models.Book;

public class BookTestHelper {

    public static Book aBook() {
        Book book = new Book();
        book.setGenre("Fiction");
        book.setAuthor("Gabriel García Márquez");
        book.setImage("image.jpg");
        book.setTitle("El amor en los tiempos del colera");
        book.setSubtitle("-");
        book.setPublisher("Vintage");
        book.setYear("1997");
        book.setPages(464);
        book.setIsbn("9781400034673");
        return book;
    }

    public static List<Book> aBookList() {

        Book book1 = new Book();
        book1.setGenre("Romance literature");
        book1.setAuthor("Mario Vargas Llosa\n");
        book1.setImage("image.jpg");
        book1.setTitle("El amor en los tiempos del colera");
        book1.setSubtitle("-");
        book1.setPublisher("Alfaguara");
        book1.setYear("2016");
        book1.setPages(314);
        book1.setIsbn("9788420418964");

        Book book2 = new Book();
        book2.setGenre("Fiction");
        book2.setAuthor("Gabriel García Márquez");
        book2.setImage("image.jpg");
        book2.setTitle("El amor en los tiempos del colera");
        book2.setSubtitle("-");
        book2.setPublisher("Vintage");
        book2.setYear("1997");
        book2.setPages(464);
        book2.setIsbn("9781400034673");

        Book book3 = new Book();
        book3.setGenre("Fiction");
        book3.setAuthor("Gabriel García Márquez");
        book3.setImage("image.jpg");
        book3.setTitle("Crónica de una muerte anunciada");
        book3.setSubtitle("-");
        book3.setPublisher("Bruguera");
        book3.setYear("1980");
        book3.setPages(193);
        book3.setIsbn("9788402076939");

        return Arrays.asList(book1, book2, book3);

    }
}
