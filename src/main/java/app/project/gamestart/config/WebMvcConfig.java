package app.project.gamestart.config;

import app.project.gamestart.web.interceptors.RolesInterceptor;
import app.project.gamestart.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final RolesInterceptor rolesInterceptor;

    @Autowired
    public WebMvcConfig(TitleInterceptor titleInterceptor, RolesInterceptor rolesInterceptor){
        this.titleInterceptor = titleInterceptor;
        this.rolesInterceptor = rolesInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.rolesInterceptor);
    }
}
