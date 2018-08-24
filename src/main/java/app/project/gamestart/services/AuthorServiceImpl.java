package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.enums.Country;
import app.project.gamestart.domain.models.binding.AuthorAddBindingModel;
import app.project.gamestart.domain.models.service.AuthorServiceModel;
import app.project.gamestart.repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveAuthor(AuthorServiceModel serviceModel) {
        this.authorRepository.saveAndFlush(this.modelMapper.map(serviceModel,Author.class));
    }

    @Override
    public List<AuthorServiceModel> findAll() {
        Type type = new TypeToken<List<AuthorServiceModel>>(){}.getType();

        return this.modelMapper.map(this.authorRepository.findAll(),type);
    }

    @Override
    public Set<Author> findAllByIdIn(Set<String> ids) {
        return this.authorRepository.findAllByIdIn(ids);
    }

    @Override
    public void validateAuthor(AuthorAddBindingModel bindingModel, BindingResult bindingResult) {
        if(this.authorRepository.findAllByNameAndCountryAndDateOfBirth(
                bindingModel.getName(),
                Country.valueOf(bindingModel.getCountry()),
                bindingModel.getDateOfBirth())
                .size() > 0){

            bindingResult.rejectValue("name","error.viewModel","This author already exists");
        }
    }
}
