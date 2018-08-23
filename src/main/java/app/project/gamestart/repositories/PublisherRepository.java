package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Publisher;
import app.project.gamestart.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {

    Page<Publisher> findAllByApproved(Pageable pageable, Boolean approved);

    Page<Publisher> findAllByApprovedAndCompanyNameContains(Pageable pageable, Boolean approved, String companyName);

    Publisher findFirstByUser(User user);

}
