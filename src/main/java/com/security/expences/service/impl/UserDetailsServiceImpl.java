package com.security.expences.service.impl;

import com.security.expences.model.User;
import com.security.expences.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        if(user.getUsername().equalsIgnoreCase("ADMIN") || user.getPlatformRole().equalsIgnoreCase("ADMIN")){
            final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), roles);
        }
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), roles);
    }
}
