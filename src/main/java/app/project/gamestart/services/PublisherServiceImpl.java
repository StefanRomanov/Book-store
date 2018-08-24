package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.PublisherAddBindingModel;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import app.project.gamestart.exceptions.PublisherNotFoundException;
import app.project.gamestart.exceptions.UserNotFoundException;
import app.project.gamestart.repositories.PublisherRepository;
import app.project.gamestart.repositories.UserRoleRepository;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.io.IOException;

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
    public void addPublisher(PublisherServiceModel serviceModel, String userId) {

        Publisher publisher = this.modelMapper.map(serviceModel,Publisher.class);
        if(publisher == null){
            throw new PublisherNotFoundException();
        }

        User user = this.userService.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException();
        }


        this.userService.addRole(user.getId(),"PENDING");
        publisher.setApproved(false);
        publisher.setUser(user);

        this.publisherRepository.saveAndFlush(publisher);
    }

    @Override
    public PublisherServiceModel getPublisherById(String id) {
        Publisher publisher = this.publisherRepository.findById(id).orElse(null);
        if(publisher == null){
            throw new PublisherNotFoundException();
        }

        return this.modelMapper.map(publisher,PublisherServiceModel.class);
    }

    @Override
    public void approvePublisher(String publisherId) {
        Publisher publisher = this.publisherRepository.findById(publisherId).orElse(null);

        if(publisher == null){
            throw new PublisherNotFoundException();
        }

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
    public void delete(String id) throws IOException {
        Publisher publisher = this.publisherRepository.findById(id).orElse(null);

        if(publisher == null){
            throw new PublisherNotFoundException();
        }

        User user = publisher.getUser();
        user.getAuthorities().clear();
        user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));

        for (Book book : publisher.getPublishedBooks()) {
            this.bookService.deleteBook(book.getId());
        }

        this.publisherRepository.delete(publisher);
    }

    @Override
    public void edit(String id, PublisherServiceModel serviceModel){

        Publisher publisher = this.publisherRepository.findById(id).orElse(null);

        if(publisher == null){
            throw new PublisherNotFoundException();
        }

        publisher.setCompanyName(serviceModel.getCompanyName());
        publisher.setBillingAddress(serviceModel.getBillingAddress());
        publisher.setCompanyEmail(serviceModel.getCompanyEmail());
        publisher.setCountry(serviceModel.getCountry());
        publisher.setCity(serviceModel.getCity());
        publisher.setLegalForm(serviceModel.getLegalForm());
        publisher.setPostalCode(serviceModel.getPostalCode());
        publisher.setVatNumber(serviceModel.getVatNumber());

        this.publisherRepository.saveAndFlush(publisher);
    }

    @Override
    public void validateRegister(BindingResult bindingResult, PublisherAddBindingModel bindingModel) {
        if(this.publisherRepository.findFirstByCompanyName(bindingModel.getCompanyName()) != null){
            bindingResult.rejectValue("companyName","error.viewModel","Company already registered !");
        }

        if(this.publisherRepository.findFirstByCompanyEmail(bindingModel.getCompanyEmail()) != null){
            bindingResult.rejectValue("companyEmail","error.viewModel","Email already taken !");
        }

        if(this.publisherRepository.findFirstByVatNumber(bindingModel.getVatNumber()) != null){
            bindingResult.rejectValue("vatNumber","error.viewModel","VAT already registered !");
        }
    }
}
