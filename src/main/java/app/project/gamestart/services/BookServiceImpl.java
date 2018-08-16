package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.repositories.BookRepository;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    @Async
    public void addGame(BookAddServiceModel bindingModel) throws IOException {

        BookAddServiceModel bookModel = this.modelMapper.map(bindingModel, BookAddServiceModel.class);

        Book bookFinal = this.modelMapper.map(bookModel, Book.class);

        bookFinal.setTextFile(this.cloudinaryService.uploadImage(bookModel.getTextFile()));
        bookFinal.setCoverImageUrl(this.cloudinaryService.uploadImage(bookModel.getCoverImageUrl()));

        String test = "";

        this.bookRepository.saveAndFlush(bookFinal);
    }

    @Override
    public Book getOneById(String id) {
        return this.bookRepository.getOne(id);
    }

    @Override
    public byte[] downloadTextFile(String bookId) throws IOException {
        Book book = this.bookRepository.getOne(bookId);

        InputStream inputStream = new URL(book.getCoverImageUrl()).openStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        inputStream.close();

        return bytes;
    }


}
