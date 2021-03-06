package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.PublisherAddBindingModel;
import app.project.gamestart.domain.models.service.PublisherServiceModel;
import app.project.gamestart.domain.models.views.AllPublishersViewModel;
import app.project.gamestart.domain.models.views.BookAllView;
import app.project.gamestart.domain.models.views.PublisherApproveViewModel;
import app.project.gamestart.services.PublisherService;
import app.project.gamestart.services.UserService;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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

    @Autowired
    public PublisherController(PublisherService publisherService, ModelMapper modelMapper) {
        this.publisherService = publisherService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    public ModelAndView register(){
        return super.view("/publishers/register",new PublisherAddBindingModel(), "Become a partner");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("viewModel") PublisherAddBindingModel bindingModel, BindingResult bindingResult, Authentication authentication){

        this.publisherService.validateRegister(bindingResult,bindingModel);

        if(bindingResult.hasErrors()){
            return super.view("/publishers/register",bindingModel, "Become a partner");
        }

        PublisherServiceModel serviceModel = this.modelMapper.map(bindingModel,PublisherServiceModel.class);
        User user = (User) authentication.getPrincipal();


        this.publisherService.addPublisher(serviceModel, user.getId());

        return super.redirect("/");
    }

    @GetMapping("/manage/{id}")
    public ModelAndView approve(@PathVariable("id") String id){
        PublisherApproveViewModel model = this.modelMapper.map(this.publisherService.getPublisherById(id), PublisherApproveViewModel.class);

        return super.view("/publishers/manage", model,"Manage publisher");
    }

    @GetMapping(value = "/api/manage", produces = "application/json")
    public @ResponseBody
    Page<AllPublishersViewModel> reviewsForManaging(Pageable pageable,
                                       @RequestParam(value = "approved") boolean approved,
                                       @RequestParam(value = "company", required = false) String title){
        Page<AllPublishersViewModel> views = null;
        if(title != null && !title.equals("")){
            views = PageMapper.mapPage(this.publisherService.getAllPublishersByApprovedAndCompanyName(pageable, approved, title),AllPublishersViewModel.class,modelMapper);
        } else {
            views = PageMapper.mapPage(this.publisherService.getAllPublishersByApproved(pageable, approved),AllPublishersViewModel.class,modelMapper);
        }

        return  views;
    }


    @GetMapping("/manage")
    public ModelAndView manage(){
        return super.view("/publishers/all",null, "Publishers");
    }

    @PostMapping("/approve/{id}")
    public ModelAndView approveConfirm(@PathVariable("id") String id){
        this.publisherService.approvePublisher(id);

        return super.redirect("/");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id) throws Exception {
        this.publisherService.delete(id);

        return super.redirect("/publishers/manage");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        PublisherServiceModel serviceModel = this.publisherService.getPublisherById(id);
        if(!serviceModel.getApproved()){
            throw new AccessDeniedException("Cannot edit publishers that are not approved");
        }

        PublisherAddBindingModel bindingModel = this.modelMapper.map(serviceModel,PublisherAddBindingModel.class);

        return super.view("/publishers/edit",bindingModel,"Edit Publisher");
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@PathVariable("id") String id, @Valid @ModelAttribute("viewModel") PublisherAddBindingModel bindingModel, BindingResult bindingResult){

        PublisherServiceModel publisherServiceModel = this.modelMapper.map(bindingModel,PublisherServiceModel.class);

        if(!this.publisherService.getPublisherById(bindingModel.getId()).getApproved()){
            throw new AccessDeniedException("Cannot edit publishers that are not approved");
        }

        if(bindingResult.hasErrors()){
            return super.view("/publishers/edit",bindingModel,"Edit Publisher");
        }

        this.publisherService.edit(id,publisherServiceModel);

        return super.redirect("/publishers/manage/" + id);
    }
}
