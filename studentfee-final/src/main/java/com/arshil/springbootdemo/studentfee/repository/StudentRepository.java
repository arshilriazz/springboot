package com.arshil.springbootdemo.studentfee.repository;

import com.arshil.springbootdemo.studentfee.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByRoll(String name);
    List<Student> findAllByOrderByRoll();

}