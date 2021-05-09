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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.test.TestConstants;
import wolox.training.test.util.BookTestHelper;
import wolox.training.utils.MessageError;
import wolox.training.utils.RouteConstants;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    public static final String USER_FIELD_NAME_AUTHOR = "author";
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

    @WithMockUser
    @Test
    void givenAreBooksRegistered_whenFindAll_thenReturnAListOfBooks() throws Exception {
        Mockito.when(bookRepository.findAll()).thenReturn(mockBookList);
        mvc.perform(get(RouteConstants.BOOK_CONTROLLER_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBookList));
    }

    @WithMockUser
    @Test
    void whenFindByIDExists_thenReturnABook() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        mvc.perform(get(RouteConstants.BOOK_CONTROLLER_BASE_PATH + TestConstants.BOOK_MOCK_ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBook));
    }

    @WithMockUser
    @Test
    void whenFindByIDDoesNotExists_thenReturnNotFound() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get(RouteConstants.BOOK_CONTROLLER_BASE_PATH + TestConstants.BOOK_MOCK_ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(MessageError.BOOK_NOT_FOUND_MSG));
    }

    @Test
    void whenCreatedABook_thenReturnTheCreatedBook() throws Exception {
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
        mvc.perform(post(RouteConstants.BOOK_CONTROLLER_BASE_PATH)
                .content(jsonBook)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonBook));
    }

    @Test
    void whenCreatedABookWithNoAuthor_thenReturnBadRequest() throws Exception {
        Map<String, String> map = objectMapper.readValue(jsonBook, Map.class);
        map.remove(USER_FIELD_NAME_AUTHOR);
        mvc.perform(post(RouteConstants.BOOK_CONTROLLER_BASE_PATH)
                .content(map.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser
    @Test
    void whenDeleteABookThatExists_thenReturnNotContent() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        doNothing().when(bookRepository).deleteById(anyLong());
        mvc.perform(delete(RouteConstants.BOOK_CONTROLLER_BASE_PATH + TestConstants.BOOK_MOCK_ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser
    @Test
    void whenUpdateABookThatExists_thenReturnTheUpdatedBook() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
        mvc.perform(put(RouteConstants.BOOK_CONTROLLER_BASE_PATH + TestConstants.BOOK_MOCK_ID_0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonBook));
    }

    @WithMockUser
    @Test
    void whenUpdateABookWithIDMissMatch_thenReturnIdMissMatch() throws Exception {
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));
        Mockito.when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
        mvc.perform(put(RouteConstants.BOOK_CONTROLLER_BASE_PATH + TestConstants.BOOK_MOCK_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(MessageError.BOOK_ID_MISMATCH_MSG));
    }

    @Test
    void givenAUserIsNotAuthenticated_whenUpdateABookThatExists_thenReturnUnauthorized() throws Exception {
        mvc.perform(put(RouteConstants.BOOK_CONTROLLER_BASE_PATH + TestConstants.BOOK_MOCK_ID_0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isUnauthorized());
    }
}
