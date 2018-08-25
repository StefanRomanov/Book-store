package app.project.gamestart;

import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import app.project.gamestart.exceptions.PublisherNotFoundException;
import app.project.gamestart.exceptions.UserNotFoundException;
import app.project.gamestart.repositories.PublisherRepository;
import app.project.gamestart.services.PublisherServiceImpl;
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

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PublisherServiceTests {
    private final String AWESOME_ID = "aa";


    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Before
    public void setUp() {
        when(this.modelMapper.map(any(),eq(Publisher.class))).thenReturn(null);
        when(this.userService.getUserById(any())).thenReturn(null);
    }

    @Test(expected = PublisherNotFoundException.class)
    public void addPublisherTest_ExceptionWhenPublisherIsNull(){
        this.publisherService.addPublisher(new PublisherServiceModel(),AWESOME_ID);
    }

    @Test(expected = UserNotFoundException.class)
    public void addPublisherTest_ExceptionWhenUserIsNull(){
        when(this.modelMapper.map(any(),eq(Publisher.class))).thenReturn(new Publisher());
        this.publisherService.addPublisher(new PublisherServiceModel(),AWESOME_ID);
    }

    @Test(expected = PublisherNotFoundException.class)
    public void getPublisherTest_ExceptionWhenPublisherIsNull(){
        this.publisherService.addPublisher(new PublisherServiceModel(),AWESOME_ID);
    }

    @Test(expected = PublisherNotFoundException.class)
    public void approvePublisherTest_ExceptionWhenPublisherIsNull(){
        this.publisherService.approvePublisher(AWESOME_ID);
    }

    @Test(expected = PublisherNotFoundException.class)
    public void editPublisherTest_ExceptionWhenPublisherIsNull(){
        this.publisherService.edit(AWESOME_ID, new PublisherServiceModel());
    }

    @Test(expected = PublisherNotFoundException.class)
    public void deletePublisherTest_ExceptionWhenPublisherIsNull(){
        try {
            this.publisherService.delete(AWESOME_ID);
        } catch (IOException e) {
            //Expected
        }
    }
}
