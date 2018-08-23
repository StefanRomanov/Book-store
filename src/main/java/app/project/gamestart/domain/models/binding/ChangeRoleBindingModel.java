package app.project.gamestart.domain.models.binding;

public class ChangeRoleBindingModel {
    private String id;
    private String role;

    public ChangeRoleBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
