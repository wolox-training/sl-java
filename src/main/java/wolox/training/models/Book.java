package wolox.training.models;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

    private static final String CHECK_NOT_NULL_MESSAGE = "Please check the Object supplied to %s, its null!";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String genre;

    @NotNull
    private String author;

    @NotNull
    private String image;

    @NotNull
    private String title;

    @NotNull
    private String subtitle;

    @NotNull
    private String publisher;

    @NotNull
    private String year;

    @NotNull
    private int pages;

    @NotNull
    private String isbn;

    public Book() {
    }

    public long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = checkNotNull(author, CHECK_NOT_NULL_MESSAGE, "author");
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = checkNotNull(image, CHECK_NOT_NULL_MESSAGE, "image");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = checkNotNull(title, CHECK_NOT_NULL_MESSAGE, "title");
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = checkNotNull(subtitle, CHECK_NOT_NULL_MESSAGE, "subtitle");
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = checkNotNull(publisher, CHECK_NOT_NULL_MESSAGE, "publisher");
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = checkNotNull(year, CHECK_NOT_NULL_MESSAGE, "year");
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = checkNotNull(pages, CHECK_NOT_NULL_MESSAGE, "pages");
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = checkNotNull(isbn, CHECK_NOT_NULL_MESSAGE, "isbn");
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year='" + year + '\'' +
                ", pages=" + pages +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
