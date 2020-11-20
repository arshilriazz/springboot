package com.arshil.springbootdemo.studentfee.controller;

import com.arshil.springbootdemo.studentfee.entity.Student;
import com.arshil.springbootdemo.studentfee.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    @Autowired
    StudentService studentService;
    @GetMapping("/getStudents")
    public ResponseEntity<List<Student>> getStudents() {
       return ResponseEntity.ok(studentService.findAll());
    }
}
