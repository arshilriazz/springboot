package com.arshil.springbootdemo.studentfee.service;

import com.arshil.springbootdemo.studentfee.config.UserPrincipal;
import com.arshil.springbootdemo.studentfee.entity.User;
import com.arshil.springbootdemo.studentfee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }
}
