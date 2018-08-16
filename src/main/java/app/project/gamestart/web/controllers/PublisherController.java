package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.PublisherAddBindingModel;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import app.project.gamestart.domain.models.service.UserServiceModel;
import app.project.gamestart.domain.models.views.PublisherApproveViewModel;
import app.project.gamestart.services.PublisherService;
import app.project.gamestart.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/publishers")
public class PublisherController extends BaseController{
    private final PublisherService publisherService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public PublisherController(PublisherService publisherService, ModelMapper modelMapper, UserService userService) {
        this.publisherService = publisherService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/register")
    public ModelAndView register(){
        return super.view("/publishers/register",new PublisherAddBindingModel(), "Become a partner");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("viewModel") PublisherAddBindingModel bindingModel, BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors()){
            return super.view("/publishers/register",bindingModel, "Become a partner");
        }

        PublisherServiceModel serviceModel = this.modelMapper.map(bindingModel,PublisherServiceModel.class);
        User user = (User) authentication.getPrincipal();

        this.publisherService.addPublisher(serviceModel, user.getId(), bindingModel.getSameEmail());

        return super.redirect("/", "Home");
    }

    @GetMapping("/approve/{id}")
    public ModelAndView approve(@PathVariable("id") String id){
        PublisherApproveViewModel model = this.modelMapper.map(this.publisherService.getPublisherById(id), PublisherApproveViewModel.class);

        return super.view("/publishers/approve", model,"Approve publisher");
    }

    @PostMapping("/approve/{id}")
    public ModelAndView approveConfirm(@PathVariable("id") String id){
        this.publisherService.approvePublisher(id);

        return super.redirect("/",null,"Home");
    }
}
