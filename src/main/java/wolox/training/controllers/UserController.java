package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.utils.MessageError;
import wolox.training.utils.RouteConstants;

@RestController
@RequestMapping(RouteConstants.USERS_CONTROLLER_BASE_PATH)
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

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
    public User findOne(@PathVariable long id) {
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
        if (user.getId() != id) {
            throw new IdMismatchException(MessageError.USER_ID_MISMATCH_MSG);
        }
        findOne(id);
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
}
