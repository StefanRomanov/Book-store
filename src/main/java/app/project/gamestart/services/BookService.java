package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.models.binding.BookEditBindingModel;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookEditServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface BookService {
    void addBook(BookAddServiceModel model, String userId) throws IOException;

    BookServiceModel getOneById(String id);

    byte[] downloadTextFile(String bookId) throws IOException;

    //Page<BookServiceModel> getAllBooksByApproved(Pageable pageable, boolean approved);
//
    //Page<BookServiceModel> getAllBooksByApprovedAndTitle(Pageable pageable, boolean approved, String title);
//
    //Page<BookServiceModel> getAllBooksByApprovedAndTitleAndGenre(Pageable pageable, boolean approved, String title, String genre);

    void bookApprove(String bookId);

    void editBook(String bookId, BookEditServiceModel model) throws Exception;

    void deleteBook(String bookId) throws Exception;

    Page<BookServiceModel> getMyBooksList(Pageable pageable, boolean approved,String title,String genre, String userId);

    Page<BookServiceModel> getPublishedBooksList(Pageable pageable, boolean approved,String title,String genre, String userId);

    Page<BookServiceModel> getAllBooksList(Pageable pageable,boolean approved, String title, String genre);

    List<BookServiceModel> getFiveTopRatedBooks();
}
