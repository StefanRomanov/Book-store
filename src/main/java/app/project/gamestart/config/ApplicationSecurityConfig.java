package app.project.gamestart.config;


import app.project.gamestart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public ApplicationSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();

        repository.setSessionAttributeName("_csrf");

        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**","/img/**","/templates/**","/","/books/all", "/books/details/**","/reviews/api/**","/books/api/all").permitAll()
                    .antMatchers( "/users/login", "/users/register", "/users/login-error").anonymous()
                    .antMatchers("/publishers/register").hasAuthority("USER")
                    .antMatchers("/books/add", "/books/published", "/books/api/published").hasAnyAuthority("PARTNER")
                    .antMatchers("/books/buy/**","/reviews/add/**", "/books/my", "/books/api/my").hasAnyAuthority("USER","PARTNER","PENDING")
                    .antMatchers("/authors/add").hasAnyAuthority("PARTNER","ADMIN","ROOT")
                    .antMatchers("/books/manage","/books/api/manage","/books/approve/**",
                                                "/books/edit/**","/publishers/manage", "/publishers/manage/**",
                                                "/publishers/approve/**", "/users/roles", "/users/rolechange",
                                                "/books/delete/**", "/publishers/delete/**","/reports/all","reports/api/all","/publishers/edit/**").hasAnyAuthority("ADMIN", "ROOT")
                    .anyRequest().authenticated()
                .and()
                    .csrf()
                    .csrfTokenRepository(this.csrfTokenRepository())
                .and()
                    .formLogin()
                    .loginPage("/users/login")
                    .loginProcessingUrl("/users/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/users/login?error")
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember")
                    .rememberMeCookieName("rememberMeCookie")
                    .key("48433e39-e610-4a2c-926c-f86d46f5360a")
                    .tokenValiditySeconds(6000)
                    .userDetailsService(userService)
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/forbidden");
    }
}
