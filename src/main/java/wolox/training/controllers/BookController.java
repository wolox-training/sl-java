package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping(BookController.BOOK_CONTROLLER_BASE_PATH)
public class BookController {

    public static final String BOOK_CONTROLLER_BASE_PATH = "/api/books";
    public static final String PATH_VARIABLE_BOOK_ID = "/{id}";
    public static final String BOOK_ID_MISMATCH_MSG = "book ID does not match";
    public static final String BOOK_NOT_FOUND_MSG = "book not found";
    @Autowired
    BookRepository bookRepository;

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
     * @return the book corresponding to the requested id
     */
    @GetMapping(PATH_VARIABLE_BOOK_ID)
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND_MSG));

    }

    /**
     * Create a book record
     *
     * @param book: Book to be created (Book)
     * @return
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
    @DeleteMapping(PATH_VARIABLE_BOOK_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        findOne(id);
        bookRepository.deleteById(id);
    }

    /**
     * Update a book by its ID
     *
     * @param book: Book to be updated (Book) RequestBody
     * @param id: Book identifier (Long) PathVariable
     * @return
     */

    @PutMapping(PATH_VARIABLE_BOOK_ID)
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException(BOOK_ID_MISMATCH_MSG);
        }
        findOne(id);
        return bookRepository.save(book);
    }

    /**
     * Greetings! with a name
     *
     * @param name: Optional name of who is going to greet (String)
     * @param model: Contains the data that appears in the view (Model)
     * @return The name of the view to perform the greeting
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false,
            defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
