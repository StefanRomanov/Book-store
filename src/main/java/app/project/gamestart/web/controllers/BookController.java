package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.BaseEntity;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.BookAddBindingModel;
import app.project.gamestart.domain.models.binding.BookEditBindingModel;
import app.project.gamestart.domain.models.binding.ReviewAddBindingModel;
import app.project.gamestart.domain.models.service.BookAddServiceModel;
import app.project.gamestart.domain.models.service.BookEditServiceModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.domain.models.views.AuthorViewModel;
import app.project.gamestart.domain.models.views.BookAllView;
import app.project.gamestart.domain.models.views.BookDetailsView;
import app.project.gamestart.services.*;
import app.project.gamestart.util.MultipartToFileTransferer;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookController extends  BaseController {

    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final AuthorService authorService;
    private final SaleService saleService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper, AuthorService authorService, SaleService saleService, UserService userService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.saleService = saleService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        BookAddBindingModel bookAddBindingModel = new BookAddBindingModel();
        this.addAuthors(bookAddBindingModel);

        return super.view("/books/add", bookAddBindingModel, "Add Book");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("viewModel") BookAddBindingModel bindingModel, BindingResult bindingResult, Authentication authentication) throws IOException{

        if (bindingResult.hasErrors()) {
            this.addAuthors(bindingModel);
            return super.view("/books/add", bindingModel, "Add Book");
        }

        BookAddServiceModel serviceModel = this.modelMapper.map(bindingModel, BookAddServiceModel.class);

        User authUser = (User) authentication.getPrincipal();

        serviceModel.setCoverImageUrl(MultipartToFileTransferer.convertOne(bindingModel.getCoverImageUrl()));
        serviceModel.setTextFile(MultipartToFileTransferer.convertOne(bindingModel.getTextFile()));


        this.bookService.addBook(serviceModel, this.userService.getUserById(authUser.getId()).getId());

        return super.redirect("/");
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id, Authentication authentication, Model model){
        BookDetailsView view = this.modelMapper.map(this.bookService.getOneById(id), BookDetailsView.class);

        if(authentication == null){
            view.setOwner(false);
            view.setReviewed(false);
        } else {
            User authUser = (User)authentication.getPrincipal();
            User user = this.userService.getUserById(authUser.getId());
            view.setOwner(user.getBooks().stream().map(BaseEntity::getId).collect(Collectors.toList()).contains(view.getId()));
            view.setReviewed(user.getReviews().stream().map(r -> r.getBook().getId()).collect(Collectors.toList()).contains(view.getId()));
        }

        ReviewAddBindingModel addBindingModel = new ReviewAddBindingModel();

        if(model.containsAttribute("secondModel")){
            Map<String,Object> map = model.asMap();
            addBindingModel = (ReviewAddBindingModel) map.get("secondModel");
        }

        return super.view("/books/books-details",view,addBindingModel,"Details");
    }

    @GetMapping("/all")
    public ModelAndView all(){
        return super.view("/books/books-all",null,"All books");
    }

    @GetMapping("/my")
    public ModelAndView myBooks(){
        return super.view("/books/books-my",null,"My books");
    }

    @GetMapping(value = "/api/my", produces = "application/json")
    public @ResponseBody Page<BookAllView> myBooksList(Pageable pageable,
                                                       @RequestParam(value = "title",required = false) String title,
                                                       @RequestParam(value = "genre",required = false) String genre,
                                                       Authentication authentication){
        User authUser = (User)authentication.getPrincipal();

        return PageMapper.mapPage(this.bookService.getMyBooksList(pageable,true,title,genre,authUser.getId()), BookAllView.class, this.modelMapper);
    }

    @GetMapping("/published")
    public ModelAndView publishedBooks(){
        return super.view("/books/books-published",null,"My books");
    }

    @GetMapping(value = "/api/published", produces = "application/json")
    public @ResponseBody Page<BookAllView> publishedBooksList(Pageable pageable,
                                                       @RequestParam(value = "title",required = false) String title,
                                                       @RequestParam(value = "genre",required = false) String genre,
                                                       Authentication authentication){
        User authUser = (User)authentication.getPrincipal();

        return PageMapper.mapPage(this.bookService.getPublishedBooksList(pageable,true,title,genre,authUser.getId()), BookAllView.class, this.modelMapper);
    }

    @GetMapping(value = "/api/all", produces = "application/json")
    public @ResponseBody Page<BookAllView> allBooksList(Pageable pageable,
                                                        @RequestParam(value = "title",required = false) String title,
                                                        @RequestParam(value = "genre",required = false) String genre){

        return  PageMapper.mapPage(this.bookService.getAllBooksList(pageable,true,title,genre),BookAllView.class,modelMapper);
    }

    @GetMapping(value = "/download/{bookId}", produces = "application/epub+zip")
    public @ResponseBody
    byte[] download(@PathVariable("bookId") String bookId, HttpServletResponse response, Authentication authentication) throws IOException {

        User authUser = (User) authentication.getPrincipal();
        User user = this.userService.getUserById(authUser.getId());
        BookServiceModel bookServiceModel = this.bookService.getOneById(bookId);

        if(!user.getBooks().stream().map(BaseEntity::getId).collect(Collectors.toList()).contains(bookId) &&
                (!user.getAuthorities().iterator().next().getAuthority().equals("ADMIN") && !user.getAuthorities().iterator().next().getAuthority().equals("ROOT") )){
            throw new AccessDeniedException("Forbidden");
        }

        String bookTitle = bookServiceModel.getTitle();

        response.setHeader("Content-Disposition", "inline; filename=" + bookTitle + ".epub");
        return this.bookService.downloadTextFile(bookId);
    }

    @GetMapping("/buy/{bookId}")
    public ModelAndView buyBook(@PathVariable String bookId, Authentication authentication){

        User user = (User)authentication.getPrincipal();

        this.saleService.registerSale(user.getId(),bookId);

        return super.redirect("/books/all");
    }

    @GetMapping("/manage")
    public ModelAndView manageBooks(){
        return super.view("/admin/manage-books");
    }

    @GetMapping(value = "/api/manage", produces = "application/json")
    public @ResponseBody Page<BookAllView> booksForManaging(Pageable pageable,
                                                            @RequestParam(value = "approved") boolean approved,
                                                            @RequestParam(value = "title", required = false) String title){

        return  PageMapper.mapPage(this.bookService.getAllBooksList(pageable, approved, title,null),BookAllView.class,modelMapper);
    }

    @GetMapping("/approve/{id}")
    public ModelAndView approve(@PathVariable("id") String id, Authentication authentication){
        if(!authentication.getAuthorities().iterator().next().getAuthority().equals("ADMIN") &&
                !authentication.getAuthorities().iterator().next().getAuthority().equals("ROOT")){
            return super.redirect("/forbidden");
        }

        this.bookService.bookApprove(id);

        return super.redirect("/books/details/" + id);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        BookServiceModel serviceModel = this.bookService.getOneById(id);
        BookEditBindingModel bookEditBindingModel = this.modelMapper.map(serviceModel,BookEditBindingModel.class);

        return super.view("/books/books-edit", bookEditBindingModel, serviceModel.getTitle(),"Edit");
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@PathVariable("id") String id, @Valid @ModelAttribute BookEditBindingModel bookEditBindingModel, BindingResult bindingResult) throws Exception {

        if(bindingResult.hasErrors()){
            return super.view("/books/books-edit", bookEditBindingModel,this.bookService.getOneById(id).getTitle(), "Edit");
        }

        BookEditServiceModel serviceModel = this.modelMapper.map(bookEditBindingModel, BookEditServiceModel.class);

        if(bookEditBindingModel.getCoverImageUrl().getSize() > 0){
            serviceModel.setCoverImageUrl(MultipartToFileTransferer.convertOne(bookEditBindingModel.getCoverImageUrl()));
        }

        this.bookService.editBook(id,serviceModel);

        return super.redirect("/books/details/" + id);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id) throws Exception {
        this.bookService.deleteBook(id);

        return super.redirect("/books/all");
    }

    private void addAuthors(BookAddBindingModel bookAddBindingModel) {
        Type type = new TypeToken<List<AuthorViewModel>>(){}.getType();
        bookAddBindingModel.setAuthorViews(this.modelMapper.map(this.authorService.findAll(),type));
    }
}
