package app.project.gamestart.services;

import app.project.gamestart.domain.entities.UserRole;
import app.project.gamestart.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRole> findAll() {
        return this.userRoleRepository.findAll();
    }

    @Override
    public void saveRole(UserRole userRole) {
        this.userRoleRepository.saveAndFlush(userRole);
    }

    @Override
    public UserRole findByAuthority(String authority) {
        return this.userRoleRepository.findByAuthority(authority);
    }
}
