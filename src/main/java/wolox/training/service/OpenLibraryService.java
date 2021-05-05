package wolox.training.service;

import wolox.training.models.dto.BookInfoDto;

public interface OpenLibraryService {

    BookInfoDto bookInfo(String isbn);
}
