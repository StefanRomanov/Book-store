package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.models.views.BookAllView;
import app.project.gamestart.services.BookService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Type;
import java.util.List;

@Controller
public class HomeController  extends BaseController{

    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/")
    public ModelAndView index(){

        Type type = new TypeToken<List<BookAllView>>(){}.getType();

        List<BookAllView> books = this.modelMapper.map(this.bookService.getTopRatedBooks(),type);
        return super.view("index",books,"Welcome");
    }

    @GetMapping("/forbidden")
    public ModelAndView forbidden(){
        return super.view("/errors/forbidden",null,"403 Forbidden");
    }
}
