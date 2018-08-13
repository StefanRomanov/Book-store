package app.project.gamestart.web.controllers;

import app.project.gamestart.services.CloudinaryService;
import app.project.gamestart.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.concurrent.Future;

@Controller
public class HomeController  extends BaseController{

    private GameService gameService;
    private CloudinaryService cloudinaryService;

    @Autowired
    public HomeController(GameService gameService, CloudinaryService cloudinaryService) {
        this.gameService = gameService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping(value = "/")
    public ModelAndView index(){

        return super.view("index",null,"Welcome");
    }
}
