package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.IdMismatchException;
import wolox.training.exceptions.NotFoundException;
import wolox.training.models.User;
import wolox.training.models.dto.PasswordDto;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.MessageError;
import wolox.training.utils.RouteConstants;

@RestController
@RequestMapping(RouteConstants.USERS_CONTROLLER_BASE_PATH)
@Api
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all the users registered
     *
     * @return all the users registered
     */
    @GetMapping()
    public Iterable findAll() {
        return userRepository.findAll();
    }

    /**
     * Find a user by its id
     *
     * @param id: User identifier (Long)
     *
     * @return the user corresponding to the requested id
     */
    @GetMapping(RouteConstants.PATH_VARIABLE_USER_ID)
    @ApiOperation(value = "Find a user by its id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    public User findOne(
            @ApiParam(value = "id to find the user", example = "1", required = true)
            @PathVariable long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(MessageError.USER_NOT_FOUND_MSG));
    }

    /**
     * Create a user record
     *
     * @param user: User to be created (Book)
     *
     * @return User created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Delete a user by its ID
     *
     * @param id: User identifier (Long)
     */
    @DeleteMapping(RouteConstants.PATH_VARIABLE_USER_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        findOne(id);
        userRepository.deleteById(id);
    }

    /**
     * Update a user by its ID
     *
     * @param id:   User identifier (Long) PathVariable
     * @param user: User to be updated (Book) RequestBody
     *
     * @return User uptaded
     */
    @PutMapping(RouteConstants.PATH_VARIABLE_USER_ID)
    public User update(@PathVariable long id, @RequestBody User user) {
        if (!user.getId().equals(id)) {
            throw new IdMismatchException(MessageError.USER_ID_MISMATCH_MSG);
        }
        User userBD = findOne(id);
        user.setPassword(userBD.getPassword());
        return userRepository.save(user);
    }

    /**
     * Add a book to the user's book list
     *
     * @param id:     User identifier (Long)
     * @param bookId: Book identifier (Long)
     *
     * @return User with associated book
     */
    @PutMapping(RouteConstants.PATH_VARIABLE_USER_ID +
            RouteConstants.USER_BOOKS_PATH + RouteConstants.PATH_VARIABLE_USER_BOOK_ID)
    public User addBook(@PathVariable long id, @PathVariable long bookId) {
        User user = findOne(id);
        user.addBook(bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException(MessageError.BOOK_NOT_FOUND_MSG)));
        return userRepository.save(user);
    }

    /**
     * Remove a book to the user's book list
     *
     * @param id:     User identifier (Long)
     * @param bookId: Book identifier (Long)
     *
     * @return User without associated book
     */
    @DeleteMapping(RouteConstants.PATH_VARIABLE_USER_ID +
            RouteConstants.USER_BOOKS_PATH + RouteConstants.PATH_VARIABLE_USER_BOOK_ID)
    public User removeBook(@PathVariable long id, @PathVariable long bookId) {
        User user = findOne(id);
        user.removeBook(bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException(MessageError.BOOK_NOT_FOUND_MSG)));
        return userRepository.save(user);
    }

    /**
     * @param id:          User identifier (Long)
     * @param newPassword: new password to assign (String)
     *
     * @return User uptaded
     */
    @PatchMapping(RouteConstants.PATH_VARIABLE_USER_ID + RouteConstants.USER_PASSWORD_PATH)
    public User changePassword(@PathVariable long id, @RequestBody PasswordDto newPassword) {
        User user = findOne(id);
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves the username of the logged in user
     *
     * @param authentication: Authentication context (Authentication)
     *
     * @return Username username of the logged in user
     */
    @GetMapping(value = RouteConstants.USER_USERNAME_PATH)
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
}
