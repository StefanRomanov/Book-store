package app.project.gamestart.domain.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {
    private String username;
    private String password;


    public UserLoginBindingModel() {
    }

    @NotEmpty
    @Min(3)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty
    @Min(3)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
