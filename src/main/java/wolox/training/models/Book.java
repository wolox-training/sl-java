package wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.MessageError.CHECK_ARGUMENT_EMPTY_MESSAGE;
import static wolox.training.utils.MessageError.CHECK_NOT_NULL_MESSAGE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.springframework.util.ObjectUtils;

@Entity
public class Book {

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
        checkNotNull(author, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(author), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        checkNotNull(image, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(image), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        checkNotNull(title, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(title), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        checkNotNull(subtitle, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(subtitle), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        checkNotNull(publisher, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(publisher), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        checkNotNull(year, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(year), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = checkNotNull(pages, CHECK_NOT_NULL_MESSAGE);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        checkNotNull(isbn, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(isbn), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.isbn = isbn;
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
