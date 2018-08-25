package app.project.gamestart.services;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.entities.UserRole;
import app.project.gamestart.domain.models.binding.ChangeEmailBindingModel;
import app.project.gamestart.domain.models.binding.ChangePasswordBindingModel;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import app.project.gamestart.domain.models.service.UserServiceModel;
import app.project.gamestart.exceptions.UserNotFoundException;
import app.project.gamestart.repositories.UserRepository;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserRoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = this.userRepository.getFirstByUsername(s);
        if(user == null){
            throw new UserNotFoundException();
        }


        return user;
    }

    @Override
    public void saveUser(UserRegisterBindingModel model) {

        User user = this.modelMapper.map(model,User.class);

        if(user == null){
            throw new UserNotFoundException();
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        if(this.userRepository.findAll().size() < 1){
            user.getAuthorities().add(this.roleService.findByAuthority("ROOT"));
        } else {
            user.getAuthorities().add(this.roleService.findByAuthority("USER"));
        }

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public User getUserById(String userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if(user == null){
            throw new UserNotFoundException();
        }

        return user;
    }

    @Override
    public void addRole(String userId, String role){
        User user = this.userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }
        UserRole userRole = this.roleService.findByAuthority(role);

        user.getAuthorities().clear();

        user.getAuthorities().add(userRole);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public Page<UserServiceModel> getAllUsersPage(Pageable pageable, String userId) {

        List<UserRole> allowedRoles = new ArrayList<>();
        allowedRoles.add(this.roleService.findByAuthority("ADMIN"));
        allowedRoles.add(this.roleService.findByAuthority("USER"));

        Page<UserServiceModel> serviceModels = PageMapper.mapPage(this.userRepository.findAllByAuthoritiesInAndIdNot(pageable,allowedRoles,userId), UserServiceModel.class,modelMapper);

        return serviceModels;
    }

    @Override
    public Page<UserServiceModel> getUserPageByUsername(Pageable pageable, String username, String userId) {

        List<UserRole> allowedRoles = new ArrayList<>();
        allowedRoles.add(this.roleService.findByAuthority("ADMIN"));
        allowedRoles.add(this.roleService.findByAuthority("USER"));

        Page<UserServiceModel> serviceModels = PageMapper.mapPage(this.userRepository.findFirstByAuthoritiesInAndIdNotAndUsernameContains(pageable,allowedRoles,userId, username), UserServiceModel.class,modelMapper);

        return serviceModels;
    }

    @Override
    public void changeRole(String userId, String role) {
        User user = this.userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }

        if(user.getAuthorities().iterator().next().getAuthority().equals("ROOT") ||
                user.getAuthorities().iterator().next().getAuthority().equals("PARTNER") ){
            throw new AccessDeniedException("Can't change ROOT or PARTNER role");
        }

        user.getAuthorities().clear();
        user.getAuthorities().add(this.roleService.findByAuthority(role));

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user = this.userRepository.getFirstByUsername(username);

        if(user == null){
            throw new UserNotFoundException();
        }

        return this.modelMapper.map(user,UserServiceModel.class);
    }

    @Override
    public void changeEmail(ChangeEmailBindingModel bindingModel, String userId) {

        User user = this.userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new UserNotFoundException();
        }

        user.setEmail(bindingModel.getEmail());

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void changePassword(ChangePasswordBindingModel bindingModel, String userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if(user == null){
            throw new UserNotFoundException();
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(bindingModel.getPassword()));

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void validatePassword(BindingResult bindingResult, ChangePasswordBindingModel bindingModel){
        if(!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())){
            bindingResult.rejectValue("password","error.viewModel","Password doesn't match");
        }
    }

    @Override
    public void validateEmail(BindingResult bindingResult, ChangeEmailBindingModel bindingModel){
        if(this.userRepository.findFirstByEmail(bindingModel.getEmail()) != null){
            bindingResult.rejectValue("email","error.viewModel","Email already taken !");
        }
    }

    @Override
    public void validateRegister(BindingResult bindingResult, UserRegisterBindingModel bindingModel){
        if(this.userRepository.getFirstByUsername(bindingModel.getUsername()) != null){
            bindingResult.rejectValue("username","error.viewModel","User already exists !");
        }

        if(this.userRepository.findFirstByEmail(bindingModel.getEmail()) != null){
            bindingResult.rejectValue("email","error.viewModel","Email already taken !");
        }

        if(!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())){
            bindingResult.rejectValue("password","error.viewModel","Password doesn't match");
        }
    }
}
