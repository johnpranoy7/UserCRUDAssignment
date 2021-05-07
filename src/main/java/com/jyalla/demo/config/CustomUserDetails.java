package com.jyalla.demo.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.jyalla.demo.exception.UserNotFoundException;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.UserService;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    UserService userService;
    private User user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> roles = new ArrayList<>();
        user = userService.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException(username);
        roles.add(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

}
