package app.project.gamestart;

import app.project.gamestart.constants.Constants;
import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookEditServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.exceptions.AuthorNotFoundException;
import app.project.gamestart.exceptions.BookNotFoundException;
import app.project.gamestart.exceptions.PublisherNotFoundException;
import app.project.gamestart.exceptions.UserNotFoundException;
import app.project.gamestart.repositories.BookRepository;
import app.project.gamestart.repositories.PublisherRepository;
import app.project.gamestart.services.AuthorService;
import app.project.gamestart.services.BookServiceImpl;
import app.project.gamestart.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BookServiceTests {

    private final String AWESOME_ID = "aa";

    @Mock
    private BookRepository bookRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private AuthorService authorService;
    @Mock
    private UserService userService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Before
    public void setUp() {
        when(this.modelMapper.map(any(),eq(Book.class))).thenAnswer(a -> new Book());
        when(this.modelMapper.map(any(),eq(BookAddServiceModel.class))).thenAnswer(a -> new BookAddServiceModel());
        when(this.userService.getUserById(any())).thenReturn(null);
    }

    @Test(expected = UserNotFoundException.class)
    public void addBookTest_ExceptionWhenUserIsNull(){
        this.bookService.addBook(new BookAddServiceModel(),AWESOME_ID);
    }

    @Test(expected = PublisherNotFoundException.class)
    public void addBookTest_ShouldThrowExceptionsWhenPublisherIsNull(){
        when(this.userService.getUserById(any())).thenAnswer(a -> new User());

         this.bookService.addBook(new BookAddServiceModel(),AWESOME_ID);
    }

    @Test(expected = AuthorNotFoundException.class)
    public void addBookTest_ShouldThrowExceptionsWhenAuthorIsNull(){
        when(this.userService.getUserById(any())).thenAnswer(a -> new User());
        when(this.publisherRepository.findFirstByUser(any())).thenAnswer(a -> new Publisher());
        when(this.authorService.findAllByIdIn(any())).thenAnswer(a -> new HashSet<>());

        this.bookService.addBook(new BookAddServiceModel(),AWESOME_ID);
    }

    @Test(expected = BookNotFoundException.class)
    public void getOneById_ShouldThrowExceptionIfBookIsNull(){
        this.bookService.getOneById(AWESOME_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void downloadTextFile_ShouldThrowExceptionIfUserIsNull(){
        try {
            this.bookService.downloadTextFile(AWESOME_ID,AWESOME_ID);
        } catch (IOException ignored) {
            //Expected
        }
    }

    @Test(expected = BookNotFoundException.class)
    public void approveBook_ShouldThrowExceptionIfBookIsNull(){
        this.bookService.bookApprove(AWESOME_ID);
    }

    @Test(expected = BookNotFoundException.class)
    public void editBook_ShouldThrowExceptionIfBookIsNull(){
        try {
            this.bookService.editBook(AWESOME_ID, new BookEditServiceModel());
        }  catch (IOException igonred) {
            //expected
        }
    }

    @Test(expected = BookNotFoundException.class)
    public void deleteBook_ShouldThrowExceptionIfBookIsNull() throws Exception {
        try {
            this.bookService.deleteBook(AWESOME_ID);
        }  catch (IOException igonred) {
            //expected
        }
    }
}
