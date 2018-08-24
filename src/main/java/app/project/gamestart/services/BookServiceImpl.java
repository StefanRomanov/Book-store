package app.project.gamestart.services;

import app.project.gamestart.constants.Constants;
import app.project.gamestart.domain.entities.*;
import app.project.gamestart.domain.enums.Genre;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookEditServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.exceptions.*;
import app.project.gamestart.repositories.BookRepository;
import app.project.gamestart.repositories.PublisherRepository;
import app.project.gamestart.repositories.ReviewRepository;
import app.project.gamestart.repositories.SaleRepository;
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
import java.nio.file.AccessDeniedException;
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
    private final ReviewRepository reviewRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           ModelMapper modelMapper,
                           PublisherRepository publisherRepository, CloudinaryService cloudinaryService,
                           UserService userService, ReviewRepository reviewRepository, SaleRepository saleRepository) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.modelMapper = modelMapper;
        this.publisherRepository = publisherRepository;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    @Async
    public void addBook(BookAddServiceModel bindingModel, String userId){

        BookAddServiceModel bookModel = this.modelMapper.map(bindingModel, BookAddServiceModel.class);

        User user = this.userService.getUserById(userId);

        if (user == null){
            throw new UserNotFoundException();
        }

        Publisher publisher = this.publisherRepository.findFirstByUser(user);

        if(publisher == null){
            throw new PublisherNotFoundException();
        }

        Book bookFinal = this.modelMapper.map(bookModel, Book.class);
        bookFinal.setPublisher(publisher);

        Set<Author> authors = this.authorService.findAllByIdIn(bindingModel.getAuthors());

        if(authors.size() < 1){
            throw new AuthorNotFoundException();
        }

        bookFinal.setAuthors(authors);

        try {
            bookFinal.setTextFile(this.cloudinaryService.uploadImage(bookModel.getTextFile()));
            bookFinal.setCoverImageUrl(this.cloudinaryService.uploadImage(bookModel.getCoverImageUrl()));
        } catch (IOException e) {
            throw new FilesNotUploadedException();
        }


        this.bookRepository.saveAndFlush(bookFinal);
    }

    @Override
    public BookServiceModel getOneById(String id) {
        Book book = this.bookRepository.findById(id).orElse(null);

        if(book == null){
            throw new BookNotFoundException();
        }

        return this.modelMapper.map(book,BookServiceModel.class);
    }

    @Override
    public byte[] downloadTextFile(String bookId, String userId) throws IOException {

        User user = this.userService.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException();
        }

        if(!user.getBooks().stream().map(BaseEntity::getId).collect(Collectors.toList()).contains(bookId) &&
                (!user.getAuthorities().iterator().next().getAuthority().equals("ADMIN") && !user.getAuthorities().iterator().next().getAuthority().equals("ROOT") )) {
            throw new AccessDeniedException("Forbidden");
        }

        Book book = this.bookRepository.findById(bookId).orElse(null);
        if(book == null){
            throw  new BookNotFoundException();
        }

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
    public List<BookServiceModel> getTopRatedBooks() {
        Type type = new TypeToken<List<BookServiceModel>>(){}.getType();
        List<BookServiceModel> books = this.modelMapper.map(this.bookRepository.findAll(),type);

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
        Publisher publisher = this.publisherRepository.findFirstByUser(this.userService.getUserById(userId));
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
        Book book = this.bookRepository.findById(bookId).orElse(null);
        if(book == null){
            throw new BookNotFoundException();
        }
        book.setApproved(true);

        this.bookRepository.saveAndFlush(book);
    }

    @Override
    @Async
    public void editBook(String bookId, BookEditServiceModel model) throws IOException {

        Book book = this.bookRepository.findById(bookId).orElse(null);

        if(book == null){
            throw new BookNotFoundException();
        }

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
    public void deleteBook(String bookId) throws IOException {
        Book book = this.bookRepository.getOne(bookId);
        if(book == null){
            throw new BookNotFoundException();
        }

        for (Review review : book.getReviews()) {
            this.reviewRepository.delete(review);
        }

        for (User user : book.getUsers()){
            user.getBooks().remove(book);
        }

        for(Sale sale : this.saleRepository.findAllByBook(book)){
            this.saleRepository.delete(sale);
        }


        this.cloudinaryService.deleteImage(book.getCoverImageUrl());
        this.cloudinaryService.deleteImage(book.getTextFile());

        this.bookRepository.delete(book);
    }
}
