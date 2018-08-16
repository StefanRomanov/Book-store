package app.project.gamestart.web.controllers;

import app.project.gamestart.services.CloudinaryService;
import app.project.gamestart.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController  extends BaseController{

    private BookService bookService;
    private CloudinaryService cloudinaryService;

    @Autowired
    public HomeController(BookService bookService, CloudinaryService cloudinaryService) {
        this.bookService = bookService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping(value = "/")
    public ModelAndView index(){

        return super.view("index",null,"Welcome");
    }
}
