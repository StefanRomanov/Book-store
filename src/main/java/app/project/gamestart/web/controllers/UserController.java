package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.*;
import app.project.gamestart.domain.models.views.UserProfileView;
import app.project.gamestart.domain.models.views.UserRoleView;
import app.project.gamestart.services.UserService;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        return super.view("/users/register",new UserRegisterBindingModel(), "Register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("viewModel") UserRegisterBindingModel bindingModel, BindingResult bindingResult){

        this.userService.validateRegister(bindingResult,bindingModel);

        if(bindingResult.hasErrors()){
            return view("/users/register", bindingModel);
        }

        this.userService.saveUser(bindingModel);

        return super.redirect("/users/login");
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            return super.view("/users/login", "Invalid username or password!","Login");
        }

        if (logout != null) {
            return super.view("/users/login", "You've been logged out successfully.","Login");
        }

        return super.view("/users/login", null,"Login");
    }

    @GetMapping("/roles")
    public ModelAndView manageRoles(Pageable pageable, Authentication authentication, @RequestParam(value = "username", required = false) String username){

        User user = (User) authentication.getPrincipal();
        Page<UserRoleView> viewModels= null;

        if( username != null && !username.equals("")){
            viewModels = PageMapper.mapPage(this.userService.getUserPageByUsername(pageable,username, user.getId()), UserRoleView.class, modelMapper);
        } else {
            viewModels = PageMapper.mapPage(this.userService.getAllUsersPage(pageable, user.getId()), UserRoleView.class, modelMapper);
        }

        return super.view("/admin/roles-manager",viewModels,"All users");
    }

    @PostMapping("/rolechange")
    public ModelAndView changeRole(@ModelAttribute ChangeRoleBindingModel bindingModel){
        this.userService.changeRole(bindingModel.getId(),bindingModel.getRole());

        return super.redirect("/users/roles");
    }


    @GetMapping("/profile")
    private ModelAndView profile(Authentication authentication, Model model){
        ChangeEmailBindingModel changeEmailModel = new ChangeEmailBindingModel();

        if(model.containsAttribute("secondModel")){
            Map<String,Object> map = model.asMap();
            changeEmailModel = (ChangeEmailBindingModel) map.get("secondModel");
        }

        ChangePasswordBindingModel changePasswordModel= new ChangePasswordBindingModel();

        if(model.containsAttribute("thirdModel")){
            Map<String,Object> map = model.asMap();
            changePasswordModel = (ChangePasswordBindingModel) map.get("thirdModel");
        }

        UserProfileView view = this.modelMapper.map(this.userService.findUserByUsername(authentication.getName()),UserProfileView.class);

        return super.view("/users/profile",view,changeEmailModel,changePasswordModel,"Profile");
    }

    @PostMapping("/changemail/{id}")
    private ModelAndView changeEmail(@Valid @ModelAttribute("secondModel") ChangeEmailBindingModel bindingModel,
                                     BindingResult bindingResult, @PathVariable("id") String id, Authentication authentication, RedirectAttributes redirectAttributes){
        if(!((User) authentication.getPrincipal()).getId().equals(id)){
            throw new AccessDeniedException("Forbidden");
        }

        this.userService.validateEmail(bindingResult,bindingModel);

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.secondModel", bindingResult);
            redirectAttributes.addFlashAttribute("secondModel",bindingModel);
            return super.redirect("/users/profile");
        }
        this.userService.changeEmail(bindingModel,id);

        return super.redirect("/users/profile");
    }

    @PostMapping("/changepassword/{id}")
    private ModelAndView changePassword(@PathVariable("id") String id, @Valid @ModelAttribute("thirdModel") ChangePasswordBindingModel bindingModel,
                                        BindingResult bindingResult,Authentication authentication, RedirectAttributes redirectAttributes){
        if(!((User) authentication.getPrincipal()).getId().equals(id)){
            throw new AccessDeniedException("Forbidden");
        }

        this.userService.validatePassword(bindingResult,bindingModel);

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.thirdModel", bindingResult);
            redirectAttributes.addFlashAttribute("thirdModel",bindingModel);
            return super.redirect("/users/profile");
        }

        this.userService.changePassword(bindingModel,id);

        return super.redirect("/users/profile");
    }
}
