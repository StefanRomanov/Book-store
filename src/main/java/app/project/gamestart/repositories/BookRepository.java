package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,String> {
    List<Book> findAllByGenresIn(List<Genre> genre);
}
