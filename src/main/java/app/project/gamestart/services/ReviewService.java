package app.project.gamestart.services;

import app.project.gamestart.domain.models.binding.ReviewAddBindingModel;
import app.project.gamestart.domain.models.service.ReviewServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    void saveReview(String bookId, String userId, ReviewAddBindingModel reviewAddBindingModel);

    Page<ReviewServiceModel> getAllReviewsByBook(Pageable pageable, String bookId);

    ReviewServiceModel getOneById(String reviewId);

    void delete(String reviewId);

}
