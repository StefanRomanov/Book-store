package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BookService {
    void addGame(BookAddServiceModel model) throws IOException;
    BookServiceModel getOneById(String id);
    byte[] downloadTextFile(String bookId) throws IOException;
    Page<BookServiceModel> getAllBooks(Pageable pageable);

}
