package app.project.gamestart.config;


import app.project.gamestart.domain.entities.UserRole;
import app.project.gamestart.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */

    private final UserRoleService roleService;

    @Autowired
    public DataSeeder(UserRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        seedData();
    }

    private void seedData() {
        if(this.roleService.findAll().isEmpty()){
            UserRole adminRole = new UserRole();
            UserRole partnerRole = new UserRole();
            UserRole userRole = new UserRole();
            adminRole.setAuthority("ADMIN");
            partnerRole.setAuthority("PARTNER");
            userRole.setAuthority("USER");

            this.roleService.saveRole(adminRole);
            this.roleService.saveRole(partnerRole);
            this.roleService.saveRole(userRole);
        }
    }
}
