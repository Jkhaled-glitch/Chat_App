package com.example.chatIslem.services.user;

import com.example.chatIslem.models.user.ERole;
import com.example.chatIslem.models.user.Role;
import com.example.chatIslem.repositoies.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findRoleByName(ERole name){
        return roleRepository.findByName(name);
    }

}



