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
import wolox.training.utils.MessageError;
import wolox.training.utils.RouteConstants;

@RestController
@RequestMapping(RouteConstants.BOOK_CONTROLLER_BASE_PATH)
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public Iterable findAll() {
        return bookRepository.findAll();
    }

    @GetMapping(RouteConstants.PATH_VARIABLE_BOOK_ID)
    public Book findOne(@PathVariable Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(MessageError.BOOK_NOT_FOUND_MSG));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping(RouteConstants.PATH_VARIABLE_BOOK_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long bookId) {
        findOne(bookId);
        bookRepository.deleteById(bookId);
    }

    @PutMapping(RouteConstants.PATH_VARIABLE_BOOK_ID)
    public Book updateBook(@RequestBody Book book, @PathVariable Long bookId) {
        if (book.getId() != bookId) {
            throw new BookIdMismatchException(MessageError.BOOK_ID_MISMATCH_MSG);
        }
        findOne(bookId);
        return bookRepository.save(book);
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false,
            defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
