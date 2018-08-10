package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Game;
import app.project.gamestart.domain.entities.Review;
import app.project.gamestart.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,String> {
    List<Review> getAllByGame(Game game);
}
