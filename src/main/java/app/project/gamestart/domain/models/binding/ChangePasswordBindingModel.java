package app.project.gamestart.domain.models.binding;

import app.project.gamestart.util.constants.UserConstants;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordBindingModel {


    private String password;
    private String confirmPassword;

    public ChangePasswordBindingModel() {
    }

    @NotEmpty(message = UserConstants.PASSWORD_EMPTY_ERROR_MESSAGE)
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, message = UserConstants.PASSWORD_MIN_LENGTH_ERROR_MESSAGE)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = UserConstants.CONFIRM_PASSWORD_EMPTY_ERROR_MESSAGE)
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, message = UserConstants.PASSWORD_MIN_LENGTH_ERROR_MESSAGE)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
