package app.project.gamestart.web.controllers;

import org.apache.http.MethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionController extends BaseController{

    private static String DEFAULT_MESSAGE = "Something went wrong while processing your request !";

    @ExceptionHandler(Exception.class)
    public ModelAndView getException(Exception e){
        String message = e.getClass().isAnnotationPresent(ResponseStatus.class)
                ? e.getClass().getAnnotation(ResponseStatus.class).reason()
                : DEFAULT_MESSAGE;
        return super.view("/errors/went-wrong",message,"Error");
    }
}
