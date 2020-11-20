package com.arshil.springbootdemo.studentfee.repository;

import com.arshil.springbootdemo.studentfee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
