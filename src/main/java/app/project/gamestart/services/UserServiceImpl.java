package app.project.gamestart.services;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.entities.UserRole;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import app.project.gamestart.domain.models.service.UserServiceModel;
import app.project.gamestart.repositories.UserRepository;
import app.project.gamestart.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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
        return this.userRepository.getFirstByUsername(s);
    }

    @Override
    public void saveUser(UserRegisterBindingModel model) {

        User user = this.modelMapper.map(model,User.class);
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

        return this.userRepository.getOne(userId);
    }

    @Override
    public void addRole(String userId, String role){
        User user = this.userRepository.getOne(userId);
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
        User user = this.userRepository.getOne(userId);

        user.getAuthorities().clear();
        user.getAuthorities().add(this.roleService.findByAuthority(role));

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.getFirstByUsername(username);
    }

    @Override
    public UserServiceModel findByEmail(String email) {

        User user = this.userRepository.findFirstByEmail(email);

        if(user == null){
            return null;
        }

        return this.modelMapper.map(user,UserServiceModel.class);
    }
}
