package app.project.gamestart.interceptors;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

@Component
@Transactional
public class RolesInterceptor extends HandlerInterceptorAdapter {

    private final UserService userService;

    @Autowired
    public RolesInterceptor(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView == null) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            if(authentication.getPrincipal() != "anonymousUser") {

                User authUser = (User) authentication.getPrincipal();

                if(authUser.getId() != null){
                    User user = this.userService.getUserById(authUser.getId());

                    if(authUser.getAuthorities().iterator().next() != user.getAuthorities().iterator().next()){

                        Collection<GrantedAuthority> authorities = new HashSet<>();
                        authorities.add(user.getAuthorities().iterator().next());

                        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                                SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                "",
                                authorities);

                        ((UsernamePasswordAuthenticationToken) newAuth).setDetails(new WebAuthenticationDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(newAuth);
                    }
                }
            }
        }

        super.postHandle(request, response, handler, modelAndView);
    }
}
