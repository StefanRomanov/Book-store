package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.models.binding.GameAddBindingModel;
import app.project.gamestart.domain.models.service.GameServiceModel;
import app.project.gamestart.services.GameService;
import app.project.gamestart.util.FileToStringConverter;
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
import java.io.IOException;

@Controller
@RequestMapping("/games")
public class GameController extends  BaseController {

    private final GameService gameService;
    private final ModelMapper modelMapper;
    private final FileToStringConverter fileToStringConverter;

    @Autowired
    public GameController(GameService gameService, ModelMapper modelMapper, FileToStringConverter fileToStringConverter) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
        this.fileToStringConverter = fileToStringConverter;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        return  super.view("/games/add",new GameAddBindingModel(), "Add Game");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("viewModel") GameAddBindingModel bindingModel, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return  super.view("/games/add",bindingModel, "Add Game");
        }

        GameServiceModel gameServiceModel = this.modelMapper.map(bindingModel, GameServiceModel.class);

        gameServiceModel.setCoverImageUrl(this.fileToStringConverter.convertOne(bindingModel.getCoverImageUrl()));

        gameServiceModel.setImages(this.fileToStringConverter.convertMany(bindingModel.getImages()));

        this.gameService.addGame(gameServiceModel);

        return  super.redirect("/",null, "Hello");
    }
}
