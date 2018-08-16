package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import app.project.gamestart.repositories.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository, ModelMapper modelMapper, UserService userService) {
        this.publisherRepository = publisherRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
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

        this.publisherRepository.saveAndFlush(publisher);
    }
}
