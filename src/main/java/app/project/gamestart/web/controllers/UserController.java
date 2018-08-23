package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.ChangeRoleBindingModel;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import app.project.gamestart.domain.models.views.UserRoleView;
import app.project.gamestart.services.UserService;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
        String test = "";
        return super.view("/users/register",new UserRegisterBindingModel(), "Register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("viewModel") UserRegisterBindingModel bindingModel, BindingResult bindingResult){

        this.validateRegister(bindingResult,bindingModel);

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

    private void validateRegister(BindingResult bindingResult, UserRegisterBindingModel bindingModel){
        if(this.userService.findUserByUsername(bindingModel.getUsername()) != null){
            bindingResult.rejectValue("username","error.viewModel","User already exists !");
        }

        if(this.userService.findByEmail(bindingModel.getEmail()) != null){
            bindingResult.rejectValue("email","error.viewModel","Email already taken !");
        }

        if(!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())){
            bindingResult.rejectValue("password","error.viewModel","Password doesn't match");
        }
    }
}
