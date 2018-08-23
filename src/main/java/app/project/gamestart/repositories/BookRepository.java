package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,String> {

    @Override
    Page<Book> findAll(Pageable pageable);

    Page<Book> findAllByApprovedAndTitleContainsAndGenre(Pageable pageable,Boolean approved, String title, Genre genre);

    Page<Book> findAllByApproved(Pageable pageable,Boolean approved);

    Page<Book> findAllByApprovedAndTitleContains(Pageable pageable, Boolean approved, String title);

    Page<Book> findAllByApprovedAndUsersContaining(Pageable pageable, Boolean approved, User user);

    Page<Book> findAllByApprovedAndUsersContainingAndTitleContains(Pageable pageable, Boolean approved, User user, String title);

    Page<Book> findAllByApprovedAndUsersContainingAndTitleContainsAndGenre(Pageable pageable, Boolean approved, User user, String title, Genre genre );

    Page<Book> findAllByApprovedAndPublisher(Pageable pageable, Boolean approved, Publisher Publisher);

    Page<Book> findAllByApprovedAndPublisherAndTitleContains(Pageable pageable, Boolean approved, Publisher publisher, String title);

    Page<Book> findAllByApprovedAndPublisherAndTitleContainsAndGenre(Pageable pageable, Boolean approved, Publisher publisher, String title, Genre genre );


}
