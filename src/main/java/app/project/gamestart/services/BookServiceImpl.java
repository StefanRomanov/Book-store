package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.repositories.BookRepository;
import app.project.gamestart.util.PageMapper;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    @Async
    public void addGame(BookAddServiceModel bindingModel) throws IOException {

        BookAddServiceModel bookModel = this.modelMapper.map(bindingModel, BookAddServiceModel.class);

        Book bookFinal = this.modelMapper.map(bookModel, Book.class);

        Set<Author> authors = this.authorService.findAllByIdIn(bindingModel.getAuthors());

        bookFinal.setAuthors(authors);

        bookFinal.setTextFile(this.cloudinaryService.uploadImage(bookModel.getTextFile()));
        bookFinal.setCoverImageUrl(this.cloudinaryService.uploadImage(bookModel.getCoverImageUrl()));


        this.bookRepository.saveAndFlush(bookFinal);
    }

    @Override
    public BookServiceModel getOneById(String id) {
        return this.modelMapper.map(this.bookRepository.getOne(id),BookServiceModel.class);
    }

    @Override
    public byte[] downloadTextFile(String bookId) throws IOException {
        Book book = this.bookRepository.getOne(bookId);

        InputStream inputStream = new URL(book.getCoverImageUrl()).openStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        inputStream.close();

        return bytes;
    }

    @Override
    public Page<BookServiceModel> getAllBooks(Pageable pageable) {
        Type type = new TypeToken<Page<BookServiceModel>>(){}.getType();

        Page<BookServiceModel> serviceModels = PageMapper.mapPage(this.bookRepository.findAll(pageable),BookServiceModel.class,modelMapper);
        return serviceModels;
    }
}
