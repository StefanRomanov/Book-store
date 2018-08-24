package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Book;
import app.project.gamestart.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,String> {
    List<Sale> findAllByDateAfter(LocalDate localDate);
    List<Sale> findAllByBook(Book book);
}
