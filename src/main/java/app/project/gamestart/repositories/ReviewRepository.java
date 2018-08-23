package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.models.service.ReviewServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,String> {
    Page<Review> getAllByBook(Pageable pageable, Book book);
}
