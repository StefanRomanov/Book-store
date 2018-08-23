package app.project.gamestart.services;

import app.project.gamestart.constants.Constants;
import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookEditServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.repositories.BookRepository;
import app.project.gamestart.repositories.PublisherRepository;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;
    private final PublisherRepository publisherRepository;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           ModelMapper modelMapper,
                           PublisherRepository publisherRepository, CloudinaryService cloudinaryService, UserService userService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
        this.publisherRepository = publisherRepository;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    @Override
    @Async
    public void addBook(BookAddServiceModel bindingModel, String userId) throws IOException {

        BookAddServiceModel bookModel = this.modelMapper.map(bindingModel, BookAddServiceModel.class);

        Publisher publisher = this.publisherRepository.getOne(userId);

        Book bookFinal = this.modelMapper.map(bookModel, Book.class);
        bookFinal.setPublisher(publisher);

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
    public Page<BookServiceModel> getAllBooksList(Pageable pageable,boolean approved, String title, String genre){
        if(genre != null && !genre.equals("")){
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndTitleContainsAndGenre(pageable,approved,title,Genre.valueOf(genre)),BookServiceModel.class,modelMapper);
        } else if(title != null){
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndTitleContains(pageable,approved,title),BookServiceModel.class,modelMapper);
        } else {
            return PageMapper.mapPage(this.bookRepository.findAllByApproved(pageable,approved),BookServiceModel.class,modelMapper);
        }
    }

    @Override
    public List<BookServiceModel> getFiveTopRatedBooks() {
        Type type = new TypeToken<List<BookServiceModel>>(){}.getType();
        List<BookServiceModel> books = modelMapper.map(this.bookRepository.findAll(),type);

        books = books.stream().filter(b -> !b.reviewScore().equals("No reviews"))
                .sorted( (a, b) -> {
                                Double aScore = Double.parseDouble(a.reviewScore().replace("%",""));
                                Double bScore = Double.parseDouble(b.reviewScore().replace("%",""));
                                return bScore.compareTo(aScore);
                            })
                .limit(Constants.FRONT_PAGE_BOOK_COUNT)
                .collect(Collectors.toList());

        return books;
    }

    //@Override
    //public Page<BookServiceModel> getAllBooksByApproved(Pageable pageable, boolean approved) {
    //    Page<BookServiceModel> page = PageMapper.mapPage(this.bookRepository.findAllByApproved(pageable, approved),BookServiceModel.class,modelMapper);
    //    return page;
    //}
//
    //@Override
    //public Page<BookServiceModel> getAllBooksByApprovedAndTitle(Pageable pageable, boolean approved, String title) {
    //    return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndTitleContains(pageable, approved, title),BookServiceModel.class,modelMapper);
    //}
//
    //@Override
    //public Page<BookServiceModel> getAllBooksByApprovedAndTitleAndGenre(Pageable pageable, boolean approved, String title, String genre) {
    //    return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndTitleContainsAndGenre(pageable,approved,title,Genre.valueOf(genre)),BookServiceModel.class,modelMapper);
    //}

    @Override
    public Page<BookServiceModel> getMyBooksList(Pageable pageable, boolean approved,String title,String genre, String userId){
        User user = this.userService.getUserById(userId);
        if(genre != null && !genre.equals("")){
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndUsersContainingAndTitleContainsAndGenre(pageable,approved,user,title,Genre.valueOf(genre)),BookServiceModel.class,modelMapper);
        } else if(title != null){
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndUsersContainingAndTitleContains(pageable,approved,user,title),BookServiceModel.class,modelMapper);
        } else {
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndUsersContaining(pageable,approved,user),BookServiceModel.class,modelMapper);
        }
    }



    @Override
    public Page<BookServiceModel> getPublishedBooksList(Pageable pageable, boolean approved,String title,String genre, String userId){
        Publisher publisher = this.publisherRepository.getOne(userId);
        if(genre != null && !genre.equals("")){
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndPublisherAndTitleContainsAndGenre(pageable,approved,publisher,title,Genre.valueOf(genre)),BookServiceModel.class,modelMapper);
        } else if(title != null){
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndPublisherAndTitleContains(pageable,approved,publisher,title),BookServiceModel.class,modelMapper);
        } else {
            return PageMapper.mapPage(this.bookRepository.findAllByApprovedAndPublisher(pageable,approved,publisher),BookServiceModel.class,modelMapper);
        }
    }

    @Override
    public void bookApprove(String bookId) {
        Book book = this.bookRepository.getOne(bookId);
        book.setApproved(true);

        this.bookRepository.saveAndFlush(book);
    }

    @Override
    @Async
    public void editBook(String bookId, BookEditServiceModel model) throws Exception {

        Book book = this.bookRepository.getOne(bookId);
        book.setDescription(model.getDescription());
        book.setPrice(model.getPrice());
        book.setGenre(Genre.valueOf(model.getGenre()));
        book.setReleaseDate(model.getReleaseDate());

        if(model.getCoverImageUrl() != null){
            String coverUrl = this.cloudinaryService.uploadImage(model.getCoverImageUrl());
            String oldImg = book.getCoverImageUrl();
            book.setCoverImageUrl(coverUrl);
            cloudinaryService.deleteImage(oldImg);
        }

        this.bookRepository.saveAndFlush(book);
    }

    @Override
    public void deleteBook(String bookId) throws Exception {
        Book book = this.bookRepository.getOne(bookId);
        this.cloudinaryService.deleteImage(book.getCoverImageUrl());
        this.cloudinaryService.deleteImage(book.getTextFile());

        this.bookRepository.delete(book);
    }
}
