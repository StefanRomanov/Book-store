package app.project.gamestart.domain.models.binding;

import app.project.gamestart.util.constants.GlobalConstants;
import app.project.gamestart.util.constants.UserConstants;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {
    private String username;
    private String password;


    public UserLoginBindingModel() {
    }

    @NotEmpty
    @Min(UserConstants.PASSWORD_MIN_LENGTH)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty
    @Min(UserConstants.PASSWORD_MIN_LENGTH)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
