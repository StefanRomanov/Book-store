package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.Author;
import app.project.gamestart.domain.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author,String> {
    List<Author> findAllByNameAndCountryAndDateOfBirth(String name, Country country,LocalDate dateOfBirth);
    Set<Author> findAllByIdIn(Set<String> ids);
}
