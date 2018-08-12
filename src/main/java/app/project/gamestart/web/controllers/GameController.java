package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.models.binding.GameAddBindingModel;
import app.project.gamestart.services.GameService;
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
@RequestMapping("/games")
public class GameController extends  BaseController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        return  super.view("/games/add",new GameAddBindingModel(), "Add Game");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("viewModel") GameAddBindingModel model, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return  super.view("/games/add",model, "Add Game");
        }

        return  super.view("/games/add",new GameAddBindingModel(), "Add Game");
    }
}
