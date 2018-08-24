package app.project.gamestart.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController extends BaseController{

    @ExceptionHandler(Exception.class)
    public ModelAndView getException(){
        return super.view("/errors/forbidden");
    }
}
