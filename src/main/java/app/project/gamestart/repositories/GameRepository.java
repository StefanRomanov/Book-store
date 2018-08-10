package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Game;
import app.project.gamestart.domain.enums.GenreName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,String> {
    List<Game> findAllByGenresIn(List<GenreName> genreName);
}
