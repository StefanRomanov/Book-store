package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import app.project.gamestart.repositories.PublisherRepository;
import app.project.gamestart.repositories.UserRoleRepository;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final UserRoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, UserRoleRepository roleRepository, ModelMapper modelMapper, UserService userService, BookService bookService) {
        this.publisherRepository = publisherRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public void addPublisher(PublisherServiceModel serviceModel, String userId, Boolean sameEmail) {

        Publisher publisher = this.modelMapper.map(serviceModel,Publisher.class);
        User user = this.userService.getUserById(userId);

        if(sameEmail){
            publisher.setCompanyEmail(user.getEmail());
        }

        publisher.setApproved(false);
        publisher.setUser(user);

        this.publisherRepository.saveAndFlush(publisher);
    }

    @Override
    public PublisherServiceModel getPublisherById(String id) {
        return this.modelMapper.map(this.publisherRepository.getOne(id),PublisherServiceModel.class);
    }

    @Override
    public void approvePublisher(String publisherId) {
        Publisher publisher = this.publisherRepository.getOne(publisherId);
        publisher.setApproved(true);
        this.userService.addRole(publisher.getUser().getId(),"PARTNER");

        this.publisherRepository.saveAndFlush(publisher);
    }

    @Override
    public Page<PublisherServiceModel> getAllPublishersByApproved(Pageable pageable, boolean approved) {

        return PageMapper.mapPage(this.publisherRepository.findAllByApproved(pageable,approved),PublisherServiceModel.class,this.modelMapper);
    }

    @Override
    public Page<PublisherServiceModel> getAllPublishersByApprovedAndCompanyName(Pageable pageable, boolean approved, String title) {
        return PageMapper.mapPage(this.publisherRepository.findAllByApprovedAndCompanyNameContains(pageable,approved,title),PublisherServiceModel.class,this.modelMapper);
    }

    @Override
    public Publisher getPublisherByUserId(String userId) {
        return this.publisherRepository.findFirstByUser(this.userService.getUserById(userId));
    }

    @Override
    public void delete(String id) throws Exception {
        Publisher publisher = this.publisherRepository.getOne(id);
        User user = publisher.getUser();
        user.getAuthorities().clear();
        user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));

        for (Book book : publisher.getPublishedBooks()) {
            this.bookService.deleteBook(book.getId());
        }

        this.publisherRepository.delete(publisher);
    }
}
