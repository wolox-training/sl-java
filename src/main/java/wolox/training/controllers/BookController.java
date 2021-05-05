package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.IdMismatchException;
import wolox.training.exceptions.NotFoundException;
import wolox.training.models.Book;
import wolox.training.models.mappers.BookMapper;
import wolox.training.repositories.BookRepository;
import wolox.training.service.OpenLibraryService;
import wolox.training.utils.MessageError;
import wolox.training.utils.RouteConstants;

@RestController
@RequestMapping(RouteConstants.BOOK_CONTROLLER_BASE_PATH)
@Api
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OpenLibraryService openLibraryService;

    /**
     * Get all the books registered
     *
     * @return all the books registered
     */
    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    /**
     * Find a book by its id
     *
     * @param id: Book identifier (Long)
     *
     * @return the book corresponding to the requested id
     */

    @GetMapping(RouteConstants.PATH_VARIABLE_BOOK_ID)
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException(MessageError.BOOK_NOT_FOUND_MSG));

    }

    @GetMapping(RouteConstants.BOOKS_ISBN_PATH)
    @ApiOperation(value = "Find a book by its ISBN code", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book"),
            @ApiResponse(code = 201, message = "Successfully created book"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<Object> findByISBN(@PathVariable String isbn) {
        Optional<Book> bookOptional = bookRepository.findFirstByIsbn(isbn);
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(bookOptional.get());
        } else {
            Book book = BookMapper.bookInfoToBook(openLibraryService.bookInfo(isbn), isbn);
            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
        }
    }

    /**
     * Create a book record
     *
     * @param book: Book to be created (Book)
     *
     * @return Book created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     * Delete a book by its ID
     *
     * @param id: Book identifier (Long)
     */

    @DeleteMapping(RouteConstants.PATH_VARIABLE_BOOK_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        findOne(id);
        bookRepository.deleteById(id);
    }

    /**
     * Update a book by its ID
     *
     * @param book: Book to be updated (Book) RequestBody
     * @param id:   Book identifier (Long) PathVariable
     *
     * @return Book updated
     */

    @PutMapping(RouteConstants.PATH_VARIABLE_BOOK_ID)
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new IdMismatchException(MessageError.BOOK_ID_MISMATCH_MSG);
        }
        findOne(id);
        return bookRepository.save(book);
    }

    /**
     * Greetings! with a name
     *
     * @param name:  Optional name of who is going to greet (String)
     * @param model: Contains the data that appears in the view (Model)
     *
     * @return The name of the view to perform the greeting
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false,
            defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
