package app.project.gamestart.web.controllers;

import app.project.gamestart.domain.entities.BaseEntity;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.ReviewAddBindingModel;
import app.project.gamestart.domain.models.views.ReviewViewModel;
import app.project.gamestart.services.ReviewService;
import app.project.gamestart.services.UserService;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reviews")
public class ReviewController extends BaseController {
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, ModelMapper modelMapper, UserService userService) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping("/add/{bookId}")
    public ModelAndView add(@PathVariable("bookId") String bookId, @Valid @ModelAttribute("secondModel") ReviewAddBindingModel secondModel,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes, Authentication authentication){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.secondModel", bindingResult);
            redirectAttributes.addFlashAttribute("secondModel", secondModel);
            return super.redirect("/books/details/" + bookId);
        }

        User user = (User) authentication.getPrincipal();

        this.reviewService.saveReview(bookId,user.getId(),secondModel);

        return super.redirect("/books/details/" + bookId);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id){
        String bookId = this.reviewService.getOneById(id).getBook().getId();
        this.reviewService.delete(id);

        return super.redirect("/books/details/" + bookId);
    }

    @GetMapping(value = "/api/all", produces = "application/json")
    public @ResponseBody
    Page<ReviewViewModel> allBooksList(Pageable pageable, @RequestParam("bookId") String bookId, Authentication authentication){

        Page<ReviewViewModel> reviewViewModels = PageMapper.mapPage(this.reviewService.getAllReviewsByBook(pageable,bookId),ReviewViewModel.class,modelMapper);

        if(authentication == null){
            for(ReviewViewModel reviewViewModel : reviewViewModels.getContent()) {
                reviewViewModel.setAuthor(false);
                reviewViewModel.setCurrentRole(null);
            }
        } else {
            User authUser = (User)authentication.getPrincipal();
            User user = this.userService.getUserById(authUser.getId());

            for(ReviewViewModel reviewViewModel : reviewViewModels.getContent()) {
                reviewViewModel.setAuthor(user.getReviews().stream().map(BaseEntity::getId).collect(Collectors.toList()).contains(reviewViewModel.getId()));
                reviewViewModel.setCurrentRole(user.getAuthorities().iterator().next().getAuthority());
            }
        }

        return  reviewViewModels;
    }
}
