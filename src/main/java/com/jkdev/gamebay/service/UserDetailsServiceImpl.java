package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.User;
import com.jkdev.gamebay.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;


    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = passwordEncoder();
        User user = userService.findByUserName(username);
        if (user == null) throw new UsernameNotFoundException(username);
        else
            return new org.springframework.security.core.userdetails.User(user.getUsername(), encoder.encode(user.getPassword()), user.getAuthorities());
    }
}
