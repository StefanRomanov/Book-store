package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.models.binding.BookAddBindingModel;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.services.CloudinaryService;
import app.project.gamestart.services.BookService;
import app.project.gamestart.util.MultipartToFileTransferer;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Principal;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/books")
public class BookController extends  BaseController {

    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return super.view("/games/add", new BookAddBindingModel(), "Add Book");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("viewModel") BookAddBindingModel bindingModel, BindingResult bindingResult) throws IOException, ExecutionException, InterruptedException {

        if (bindingResult.hasErrors()) {
            return super.view("/games/add", bindingModel, "Add Book");
        }

        BookAddServiceModel serviceModel = this.modelMapper.map(bindingModel, BookAddServiceModel.class);

        serviceModel.setTextFile(MultipartToFileTransferer.convertOne(bindingModel.getCoverImageUrl()));
        serviceModel.setCoverImageUrl(MultipartToFileTransferer.convertOne(bindingModel.getTextFile()));


        this.bookService.addGame(serviceModel);

        return super.redirect("/", null, "Hello");
    }

    @GetMapping(value = "/download/{bookId}", produces = "application/epub+zip")
    public @ResponseBody
    byte[] download(Principal principal, @PathVariable("bookId") String bookId, HttpServletResponse response) {
        String bookTitle = this.bookService.getOneById(bookId).getTitle();

        try {
            response.setHeader("Content-Disposition", "inline; filename=" + bookTitle + ".epub");
            return this.bookService.downloadTextFile(bookId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File not found !");
        }
    }
}
