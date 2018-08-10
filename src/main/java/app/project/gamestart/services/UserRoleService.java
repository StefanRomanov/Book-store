package app.project.gamestart.services;

import app.project.gamestart.domain.entities.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> findAll();
    void saveRole(UserRole userRole);
    UserRole findByAuthority(String authority);
}
