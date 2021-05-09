package wolox.training.models.mappers;

import wolox.training.models.Book;
import wolox.training.models.dto.BookInfoDto;

public final class BookMapper {

    private BookMapper() {
    }

    public static Book bookInfoToBook(BookInfoDto bookInfoDto, String isbn) {
        Book book = new Book();
        book.setAuthor(bookInfoDto.getAuthors().stream().findFirst().get().getName());
        book.setPublisher(bookInfoDto.getPublishers().stream().findFirst().get().getName());
        book.setIsbn(isbn);
        book.setTitle(bookInfoDto.getTitle());
        book.setSubtitle(bookInfoDto.getSubtitle());
        book.setPages(bookInfoDto.getNumberOfPages());
        book.setYear(bookInfoDto.getPublishDate());
        book.setImage(bookInfoDto.getCover().getMedium());
        return book;
    }
}
