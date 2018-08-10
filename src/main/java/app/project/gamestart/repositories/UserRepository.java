package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User getFirstByUsername(String username);
}
