package app.project.gamestart.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordBindingModel {
    private String password;
    private String confirmPassword;

    public ChangePasswordBindingModel() {
    }

    @NotEmpty(message = "Enter password")
    @Length(min = 3, message = "Minimum 3 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty(message = "Enter password again")
    @Length(min = 3, message = "Minimum 3 characters")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
