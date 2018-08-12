package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.models.binding.UserLoginBindingModel;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import app.project.gamestart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
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
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            return super.view("/users/login", "Invalid username and password!","Login");
        }

        if (logout != null) {
            return super.view("/users/login", "You've been logged out successfully.","Login");
        }

        return super.view("/users/login", null,"Login");
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return super.redirect("/",null,"Home");
    }
}
