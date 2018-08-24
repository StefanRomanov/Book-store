package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.enums.Country;
import app.project.gamestart.domain.models.binding.AuthorAddBindingModel;
import app.project.gamestart.domain.models.service.AuthorServiceModel;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AuthorService {
    void saveAuthor(AuthorServiceModel serviceModel);
    List<AuthorServiceModel> findAll();
    Set<Author> findAllByIdIn(Set<String> ids);
    void validateAuthor(AuthorAddBindingModel bindingModel, BindingResult bindingResult);
}
