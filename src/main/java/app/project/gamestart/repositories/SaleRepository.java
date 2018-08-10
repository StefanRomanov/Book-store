package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,String> {
}
