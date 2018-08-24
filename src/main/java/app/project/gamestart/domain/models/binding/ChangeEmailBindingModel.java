package app.project.gamestart.domain.models.binding;

import app.project.gamestart.validators.annotations.EmailValidator;

import javax.validation.constraints.NotEmpty;

public class ChangeEmailBindingModel {
    private String email;

    public ChangeEmailBindingModel() {
    }

    @NotEmpty
    @EmailValidator
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
