package wolox.training.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.exceptions.NotFoundException;
import wolox.training.models.dto.BookInfoDto;
import wolox.training.service.OpenLibraryService;
import wolox.training.utils.EntityConstants;
import wolox.training.utils.MessageError;
import wolox.training.utils.RouteConstants;
import wolox.training.utils.ServiceConstants;

@PropertySource(RouteConstants.CLASSPATH_OPENLIBERTYSERVICE_PROPERTIES)
@Service
public class OpenServiceImpl implements OpenLibraryService {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    @Value(ServiceConstants.OPENLIBRARY_SERVICE_BOOK_ISBN_URL)
    private String bookIsbnUrl;

    @Value(ServiceConstants.OPENLIBRARY_SERVICE_RESPONSE_FORMAT)
    private String responseFormat;

    public OpenServiceImpl(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.valueOf(false));
    }

    @Override
    public BookInfoDto bookInfo(String isbn) {
        String url = bookIsbnUrl + isbn + responseFormat;
        ResponseEntity<Optional<HashMap>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Optional<HashMap>>() {
                });

        return response.getBody().map(data -> mapperResponseToBookInfo(data, isbn))
                .orElseThrow(() -> new NotFoundException(MessageError.BOOK_NOT_FOUND_MSG));

    }

    private BookInfoDto mapperResponseToBookInfo(HashMap response, String isbn) {
        return mapper.convertValue(response.get(EntityConstants.BOOKINFO_ISBN + isbn), BookInfoDto.class);
    }
}
