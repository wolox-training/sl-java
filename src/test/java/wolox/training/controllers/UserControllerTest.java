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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.test.util.BookTestHelper;
import wolox.training.test.util.UserTestHelper;
import wolox.training.utils.MessageError;
import wolox.training.utils.RouteConstants;


@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    private static ObjectMapper objectMapper;
    private static List<User> mockUserList;
    private static User mockUser;
    private static String jsonUserList;
    private static String jsonUser;
    @MockBean
    UserRepository userRepository;
    @MockBean
    BookRepository bookRepository;
    @Autowired
    private MockMvc mvc;

    @BeforeAll
    static void setUp() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        mockUser = UserTestHelper.aUser();
        mockUserList = UserTestHelper.aUserList();
        jsonUser = objectMapper.writeValueAsString(mockUser);
        jsonUserList = objectMapper.writeValueAsString(mockUserList);
    }

    @AfterEach
    void setup() {
        mockUser = UserTestHelper.aUser();
        mockUserList = UserTestHelper.aUserList();
    }

    @Test
    void givenAreUsersRegistered_whenFindAll_thenReturnAListOfUsers() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(mockUserList);
        mvc.perform(get(RouteConstants.USERS_CONTROLLER_BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUserList));
    }

    @Test
    void whenFindByIDExists_thenReturnAUser() throws Exception {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        mvc.perform(get(RouteConstants.USERS_CONTROLLER_BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUser));
    }

    @Test
    void whenFindByIDDoesNotExists_thenReturnNotFound() throws Exception {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get(RouteConstants.USERS_CONTROLLER_BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(MessageError.USER_NOT_FOUND_MSG));
    }

    @Test
    void whenCreatedAUser_thenReturnTheCreatedUser() throws Exception {
        Mockito.when(userRepository.save(any(User.class))).thenReturn(mockUser);
        mvc.perform(post(RouteConstants.USERS_CONTROLLER_BASE_PATH)
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonUser));
    }

    @Test
    void whenCreatedAUserWithNoUsername_thenReturnBadRequest() throws Exception {
        Map<String, String> map = objectMapper.readValue(jsonUser, Map.class);
        map.remove("username");
        mvc.perform(post(RouteConstants.USERS_CONTROLLER_BASE_PATH)
                .content(map.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenDeleteAUserThatExists_thenReturnNotContent() throws Exception {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        doNothing().when(userRepository).deleteById(anyLong());
        mvc.perform(delete(RouteConstants.USERS_CONTROLLER_BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenUpdateAUserThatExists_thenReturnTheUpdatedUser() throws Exception {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(mockUser);
        mvc.perform(put(RouteConstants.USERS_CONTROLLER_BASE_PATH + "/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUser));
    }

    @Test
    void whenUpdateAUserWithIDMissMatch_thenReturnIdMissMatch() throws Exception {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(mockUser);
        mvc.perform(put(RouteConstants.USERS_CONTROLLER_BASE_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(MessageError.USER_ID_MISMATCH_MSG));
    }

    @Test
    void whenAddBookToUser_thenReturnUserWithTheBookAdded() throws Exception {
        User mockUserWithABook = UserTestHelper.aUser();
        mockUserWithABook.addBook(BookTestHelper.aBook());
        String jsonUserWithABook = objectMapper.writeValueAsString(mockUserWithABook);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(mockUser);
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BookTestHelper.aBook()));

        mvc.perform(put
                (RouteConstants.USERS_CONTROLLER_BASE_PATH + "/0" + RouteConstants.USER_BOOKS_PATH + "/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUserWithABook));
    }

    @Test
    void whenAddBookToUserThatAlreadyHad_thenReturnBookAlreadyOwned() throws Exception {
        User mockUserWithABook = UserTestHelper.aUser();
        Book book = BookTestHelper.aBook();
        mockUserWithABook.addBook(book);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserWithABook));
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        mvc.perform(put
                (RouteConstants.USERS_CONTROLLER_BASE_PATH + "/0" + RouteConstants.USER_BOOKS_PATH + "/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(MessageError.BOOK_ALREADY_OWNED_MSG))
        ;
    }

    @Test
    void WhenRemoveBookToUser_thenReturnUserWithoutTheBook() throws Exception {
        User mockUserWithABook = UserTestHelper.aUser();
        Book book = BookTestHelper.aBook();
        mockUserWithABook.addBook(book);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserWithABook));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(mockUser);
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        mvc.perform(delete(
                RouteConstants.USERS_CONTROLLER_BASE_PATH + "/0" + RouteConstants.USER_BOOKS_PATH + "/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUser));
    }

    @Test
    void WhenRemoveBookToUserDidNotHave_thenReturnUserNotOwnTheBook() throws Exception {
        User mockUserWithABook = UserTestHelper.aUser();
        Book book = BookTestHelper.aBook();
        mockUserWithABook.addBook(book);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserWithABook));
        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BookTestHelper.aBook()));

        mvc.perform(delete(
                RouteConstants.USERS_CONTROLLER_BASE_PATH + "/0" + RouteConstants.USER_BOOKS_PATH + "/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(MessageError.BOOK_NOT_OWNED_MSG));
    }
}
