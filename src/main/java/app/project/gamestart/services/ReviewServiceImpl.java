package app.project.gamestart.services;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.models.binding.ReviewAddBindingModel;
import app.project.gamestart.domain.models.service.BookServiceModel;
import app.project.gamestart.domain.models.service.ReviewServiceModel;
import app.project.gamestart.exceptions.ReviewNotFoundException;
import app.project.gamestart.repositories.ReviewRepository;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ModelMapper modelMapper;
    private final BookService bookService;
    private final UserService userService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ModelMapper modelMapper, BookService bookService, UserService userService, ReviewRepository reviewRepository) {
        this.modelMapper = modelMapper;
        this.bookService = bookService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void saveReview(String bookId, String userId, ReviewAddBindingModel  bindingModel) {
        ReviewServiceModel reviewServiceModel = this.modelMapper.map(bindingModel,ReviewServiceModel.class);

        reviewServiceModel.setBook(this.modelMapper.map(this.bookService.getOneById(bookId), Book.class));
        reviewServiceModel.setUser(this.userService.getUserById(userId));
        reviewServiceModel.setSubmissionDate(LocalDate.now());

        this.reviewRepository.saveAndFlush(this.modelMapper.map(reviewServiceModel, Review.class));
    }

    @Override
    public Page<ReviewServiceModel> getAllReviewsByBook(Pageable pageable, String bookId) {

        Book book = this.modelMapper.map(this.bookService.getOneById(bookId),Book.class);

        return PageMapper.mapPage(this.reviewRepository.getAllByBook(pageable,book),ReviewServiceModel.class,modelMapper);
    }

    @Override
    public ReviewServiceModel getOneById(String reviewId) {

        Review review = this.reviewRepository.findById(reviewId).orElse(null);
        if(review == null){
            throw new ReviewNotFoundException();
        }

        return this.modelMapper.map(review , ReviewServiceModel.class);
    }

    @Override
    public void delete(String reviewId) {

        Review review = this.reviewRepository.findById(reviewId).orElse(null);
        if(review == null){
            throw new ReviewNotFoundException();
        }

        review.getUser().getReviews().remove(review);

        this.reviewRepository.delete(review);
    }
}
