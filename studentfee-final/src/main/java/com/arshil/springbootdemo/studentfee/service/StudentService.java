package com.arshil.springbootdemo.studentfee.service;

import com.arshil.springbootdemo.studentfee.entity.Student;
import com.arshil.springbootdemo.studentfee.entity.User;

import java.util.List;

public interface StudentService {
    public List<Student> findAll();
    public List<Student> findByRollInStudents(String roll);
    public Student findById(int id);
    public void save(Student student);
    public void deleteById(int id);
    public User findByRoll(String roll);
    public void save(User user);
    public Student findByRollStudent(String roll);
}
