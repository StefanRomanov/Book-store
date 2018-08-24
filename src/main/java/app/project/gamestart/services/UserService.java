package app.project.gamestart.services;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.ChangeEmailBindingModel;
import app.project.gamestart.domain.models.binding.ChangePasswordBindingModel;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import app.project.gamestart.domain.models.service.UserServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(UserRegisterBindingModel model);
    User getUserById(String userId);
    void addRole(String userId, String role);
    Page<UserServiceModel> getAllUsersPage(Pageable pageable, String userId);
    Page<UserServiceModel> getUserPageByUsername(Pageable pageable, String username, String userId);
    void changeRole(String userId, String role);
    UserServiceModel findUserByUsername(String username);
    void changeEmail(ChangeEmailBindingModel bindingModel, String userId);
    void changePassword(ChangePasswordBindingModel bindingModel, String userId);
    void validateRegister(BindingResult bindingResult, UserRegisterBindingModel bindingModel);
    void validatePassword(BindingResult bindingResult, ChangePasswordBindingModel bindingModel);
    void validateEmail(BindingResult bindingResult, ChangeEmailBindingModel bindingModel);
}
