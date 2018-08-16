package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.models.service.BookAddServiceModel;

import java.io.IOException;

public interface BookService {
    void addGame(BookAddServiceModel model) throws IOException;
    Book getOneById(String id);
    byte[] downloadTextFile(String bookId) throws IOException;

}
