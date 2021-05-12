package wolox.training.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
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
}
