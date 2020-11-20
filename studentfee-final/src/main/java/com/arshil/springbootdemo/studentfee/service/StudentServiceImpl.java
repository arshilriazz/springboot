package com.arshil.springbootdemo.studentfee.service;

import com.arshil.springbootdemo.studentfee.entity.Student;
import com.arshil.springbootdemo.studentfee.entity.User;
import com.arshil.springbootdemo.studentfee.repository.StudentRepository;
import com.arshil.springbootdemo.studentfee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    StudentServiceImpl() { }

    @Override
    public List<Student> findAll() {
//        return studentRepository.findAll();
        return studentRepository.findAllByOrderByRoll();
    }


    @Override
    public List<Student> findByRollInStudents(String roll) {
        List<Student> byNameList = studentRepository.findByRoll(roll);
        return byNameList;
    }

    @Override
    public Student findById(int theId) {
        Optional<Student> result = studentRepository.findById(theId);
        if(result.isPresent()) {
            return result.get();
        }
        return null;
    }

    @Override
    public void save(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteById(int theId) {
        studentRepository.deleteById(theId);
    }

    @Override
    public User findByRoll(String roll) {
        return userRepository.findByUsername(roll);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Student findByRollStudent(String roll) {
        return entityManager.find(Student.class, roll);
    }
}
