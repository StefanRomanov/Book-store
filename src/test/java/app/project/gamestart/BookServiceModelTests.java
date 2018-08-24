package app.project.gamestart;

import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.models.service.BookServiceModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BookServiceModelTests {

    @Test
    public void reviewScoreTest_ShouldReturnMaxValue(){
        BookServiceModel bookServiceModel = new BookServiceModel();
        Set<Review> reviews = new HashSet<>();

        for (int i = 0; i < 8; i++) {
            Review review = new Review();
            review.setRecommended(true);
            reviews.add(review);
        }

        bookServiceModel.setReviews(reviews);
        Assert.assertEquals("Didn't return correct score","100.00%", bookServiceModel.reviewScore());
    }

    @Test
    public void reviewScoreTest_ShouldReturnMinValue(){
        BookServiceModel bookServiceModel = new BookServiceModel();
        Set<Review> reviews = new HashSet<>();

        for (int i = 0; i < 8; i++) {
            Review review = new Review();
            review.setRecommended(false);
            reviews.add(review);
        }

        bookServiceModel.setReviews(reviews);
        Assert.assertEquals("Didn't return correct score","0.00%", bookServiceModel.reviewScore());
    }

    @Test
    public void reviewScoreTest_ShouldReturnNoReviews(){
        BookServiceModel bookServiceModel = new BookServiceModel();
        bookServiceModel.setReviews(new HashSet<>());

        Assert.assertEquals("Didn't return correct score","No reviews", bookServiceModel.reviewScore());
    }
}
