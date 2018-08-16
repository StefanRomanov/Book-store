package app.project.gamestart.services;

import app.project.gamestart.domain.models.service.PublisherServiceModel;

public interface PublisherService {
    void addPublisher(PublisherServiceModel serviceModel, String userId, Boolean sameEmail);

    PublisherServiceModel getPublisherById(String id);

    void approvePublisher(String publisherId);
}
