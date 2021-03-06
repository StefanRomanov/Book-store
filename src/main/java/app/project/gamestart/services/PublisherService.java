package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.models.binding.PublisherAddBindingModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface PublisherService {
    void addPublisher(PublisherServiceModel serviceModel, String userId);

    PublisherServiceModel getPublisherById(String id);

    void approvePublisher(String publisherId);

    Page<PublisherServiceModel> getAllPublishersByApproved(Pageable pageable, boolean approved);

    Page<PublisherServiceModel> getAllPublishersByApprovedAndCompanyName(Pageable pageable, boolean approved, String companyName);

    void delete(String id) throws Exception;
    void edit(String id, PublisherServiceModel serviceModel);

    void validateRegister(BindingResult bindingResult, PublisherAddBindingModel bindingModel);
}
