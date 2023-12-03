package com.example.chatIslem.security.services;

import com.example.chatIslem.models.user.UserModel;
import com.example.chatIslem.repositoies.UserRepository;
import com.example.chatIslem.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user=userRepository.findByUserNameOrEmail(username)
                     .orElseThrow(()->new UsernameNotFoundException("User not Found with username: "+ username));
        return UserDetailsImpl.build(user);
    }
}
