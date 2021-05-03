package wolox.training.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.test.util.BookTestHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    public static final String CONTROLLER_URL = "/api/books";
    private static ObjectMapper objectMapper;
    private static List<Book> mockBookList;
    private static Book mockBook;
    private static String jsonBookList;
    private static String jsonBook;
    @MockBean
    BookRepository bookRepository;
    @Autowired
    private MockMvc mvc;


    @BeforeAll
    static void setUp() throws JsonProcessingException {
        mockBook = BookTestHelper.aBook();
        mockBookList = BookTestHelper.aBookList();
        objectMapper = new ObjectMapper();
        jsonBook = objectMapper.writeValueAsString(mockBook);
        jsonBookList = objectMapper.writeValueAsString(mockBookList);
    }

    @Test
    void givenAreBooksRegistered_whenFindAll_thenReturnAListOfBooks() throws Exception {
        Mockito.when(bookRepository.findAll()).thenReturn(mockBookList);
        mvc.perform(get(CONTROLLER_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBookList));
    }

    @Test
    void whenFindByIDExists_thenReturnABook() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        mvc.perform(get(CONTROLLER_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBook));
    }

    @Test
    void whenFindByIDDoesNotExists_thenReturnNotFound() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get(CONTROLLER_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("book does not exits"));
    }

    @Test
    void whenCreatedABook_thenReturnTheCreatedBook() throws Exception {
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
        mvc.perform(post(CONTROLLER_URL)
                .content(jsonBook)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonBook));
    }

    @Test
    void whenCreatedABookWithNoAuthor_thenReturnBadRequest() throws Exception {
        Map<String, String> map = objectMapper.readValue(jsonBook, Map.class);
        map.remove("author");
        mvc.perform(post(CONTROLLER_URL)
                .content(map.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenDeleteABookThatExists_thenReturnNotContent() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        doNothing().when(bookRepository).deleteById(anyLong());
        mvc.perform(delete(CONTROLLER_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenUpdateABookThatExists_thenReturnTheUpdatedBook() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
        mvc.perform(put(CONTROLLER_URL + "/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBook));
    }

    @Test
    void whenUpdateABookWithIDMissMatch_thenReturnIdMissMatch() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
        mvc.perform(put(CONTROLLER_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("ID does not match"));
    }


}
