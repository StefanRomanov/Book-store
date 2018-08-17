package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,String> {
    List<Book> findAllByGenresIn(List<Genre> genre);

    @Override
    Page<Book> findAll(Pageable pageable);

    Page<Book> findAllByTitle(String title, Pageable pageable);

    Page<Book> findAllByGenresIn(Set<Genre> genres, Pageable pageable);
}
