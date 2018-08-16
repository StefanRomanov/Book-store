package app.project.gamestart.services;

import app.project.gamestart.domain.entities.User;
import app.project.gamestart.domain.models.binding.UserRegisterBindingModel;
import app.project.gamestart.domain.models.service.UserServiceModel;
import app.project.gamestart.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
            user.getAuthorities().add(this.roleService.findByAuthority("ADMIN"));
        } else {
            user.getAuthorities().add(this.roleService.findByAuthority("USER"));
        }

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public User getUserById(String userId) {

        return this.userRepository.getOne(userId);
    }
}
