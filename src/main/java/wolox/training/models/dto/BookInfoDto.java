package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BookInfoDto {

    private List<AuthorDto> authors;

    private String title;

    private String subtitle;

    private List<PublisherDto> publishers;

    @JsonProperty("publish_date")
    private String publishDate;

    @JsonProperty("number_of_pages")
    private int numberOfPages;

    private CoverDto cover;

    public BookInfoDto() {
    }

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<PublisherDto> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<PublisherDto> publishers) {
        this.publishers = publishers;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public CoverDto getCover() {
        return cover;
    }

    public void setCover(CoverDto cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "BookInfoDto{" +
                "authors=" + authors +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", publishers=" + publishers +
                ", publishDate='" + publishDate + '\'' +
                ", numberOfPages='" + numberOfPages + '\'' +
                ", cover=" + cover +
                '}';
    }
}
