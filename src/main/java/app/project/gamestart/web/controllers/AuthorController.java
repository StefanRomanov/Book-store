package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.enums.Country;
import app.project.gamestart.domain.models.binding.AuthorAddBindingModel;
import app.project.gamestart.domain.models.service.AuthorServiceModel;
import app.project.gamestart.services.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/authors")
public class AuthorController extends BaseController {
    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorController(AuthorService authorService, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        return super.view("/authors/authors-add",new AuthorAddBindingModel(), "Add author");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("viewModel") AuthorAddBindingModel bindingModel, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return super.view("/authors/authors-add",bindingModel, "Add author");
        }

        if(this.authorService.findByNameCountryAndBirthDate(
            bindingModel.getName(),
            Country.valueOf(bindingModel.getCountry()),
            bindingModel.getDateOfBirth())
                .size() > 0){

            bindingResult.rejectValue("name","error.viewModel","This author already exists");

            return super.view("/authors/authors-add",bindingModel, "Add author");
        }

        this.authorService.saveAuthor(this.modelMapper.map(bindingModel,AuthorServiceModel.class));

        return super.redirect("/");

    }
}
