package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.models.binding.BookAddBindingModel;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.domain.models.views.AuthorViewModel;
import app.project.gamestart.domain.models.views.BookAllView;
import app.project.gamestart.services.AuthorService;
import app.project.gamestart.services.BookService;
import app.project.gamestart.util.MultipartToFileTransferer;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/books")
public class BookController extends  BaseController {

    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper, AuthorService authorService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
        this.addAuthors(bookAddBindingModel);

        return super.view("/books/add", bookAddBindingModel, "Add Book");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("viewModel") BookAddBindingModel bindingModel, BindingResult bindingResult) throws IOException, ExecutionException, InterruptedException {

        if (bindingResult.hasErrors()) {
            this.addAuthors(bindingModel);
            return super.view("/books/add", bindingModel, "Add Book");
        }

        BookAddServiceModel serviceModel = this.modelMapper.map(bindingModel, BookAddServiceModel.class);

        serviceModel.setTextFile(MultipartToFileTransferer.convertOne(bindingModel.getCoverImageUrl()));
        serviceModel.setCoverImageUrl(MultipartToFileTransferer.convertOne(bindingModel.getTextFile()));


        this.bookService.addGame(serviceModel);

        return super.redirect("/", null, "Hello");
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id){
        BookServiceModel b = this.bookService.getOneById(id);
        return super.view("/books/books-details",this.bookService.getOneById(id),"Details");
    }

    @GetMapping("/all")
    public ModelAndView all(){
        return super.view("/books/books-all",null,"All books");
    }

    @GetMapping(value = "/api/all", produces = "application/json")
    public @ResponseBody Page<BookAllView> allBooksList(Pageable pageable){
        Page<BookAllView> bookAllViews = PageMapper.mapPage(this.bookService.getAllBooks(pageable),BookAllView.class,modelMapper);
        return  bookAllViews;
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

    private void addAuthors(BookAddBindingModel bookAddBindingModel) {
        Type type = new TypeToken<List<AuthorViewModel>>(){}.getType();
        bookAddBindingModel.setAuthorViews(this.modelMapper.map(this.authorService.findAll(),type));
    }
}
