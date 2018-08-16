package app.project.gamestart.services;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(UserRegisterBindingModel model);
    User getUserById(String userId);
}
