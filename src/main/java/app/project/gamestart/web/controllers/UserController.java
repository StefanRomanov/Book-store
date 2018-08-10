package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.models.binding.UserLoginBindingModel;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import app.project.gamestart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        String test = "";
        return super.view("/users/register",new UserRegisterBindingModel(), "Register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("viewModel") UserRegisterBindingModel bindingModel, BindingResult bindingResult){
        if(!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())){
            bindingResult.rejectValue("password","error.viewModel","Password doesn't match");
        }
        if(bindingResult.hasErrors()){
            return view("/users/register", bindingModel);
        }


        this.userService.saveUser(bindingModel);

        return super.redirect("/users/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return super.view("/users/login",new UserLoginBindingModel(), "Login");
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return super.redirect("/",null,"Home");
    }
}
