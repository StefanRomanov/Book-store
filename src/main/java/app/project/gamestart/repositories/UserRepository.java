package app.project.gamestart.repositories;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.entities.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User getFirstByUsername(String username);
    Page<User> findAllByAuthoritiesInAndIdNot(Pageable pageable, List<UserRole> roles, String userId);
    Page<User> findFirstByAuthoritiesInAndIdNotAndUsername(Pageable pageable, List<UserRole> roles, String userId, String username);
}
