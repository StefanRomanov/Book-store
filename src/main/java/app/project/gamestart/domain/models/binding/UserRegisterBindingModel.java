package app.project.gamestart.domain.models.binding;

import app.project.gamestart.util.constants.UserConstants;
import app.project.gamestart.web.validators.annotations.EmailValidator;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class UserRegisterBindingModel {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotEmpty(message = UserConstants.USERNAME_EMPTY_ERROR_MESSAGE)
    @Length(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH, message = UserConstants.USERNAME_LENGTH_ERROR_MESSAGE)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @EmailValidator
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = UserConstants.PASSWORD_EMPTY_ERROR_MESSAGE)
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = UserConstants.PASSWORD_LENGTH_ERROR_MESSAGE)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = UserConstants.CONFIRM_PASSWORD_EMPTY_ERROR_MESSAGE)
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = UserConstants.PASSWORD_LENGTH_ERROR_MESSAGE)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
