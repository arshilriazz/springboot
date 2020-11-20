package com.arshil.springbootdemo.studentfee.service;

import com.arshil.springbootdemo.studentfee.entity.Student;
import com.arshil.springbootdemo.studentfee.repository.StudentRepository;
import com.arshil.springbootdemo.studentfee.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;

    private List<Student> students = new ArrayList<>();

    public void addingStudents() {
        Student student1 = new Student(1, "1604-16-733-028", "Arshil",
                "Riaz", "arshil@gmail.com","Semester_1","Due");

        Student student2 = new Student(2, "1604-16-733-029", "Adil",
                "Mohd", "adil@gmail.com","Semester_1","No_Due");

        Student student3 = new Student(3, "1604-16-733-030", "Anand",
                "Shabad", "adil@gmail.com","Semester_1","Due");

        students.add(student1);
        students.add(student2);
        students.add(student3);
    }

    @Test
    public void findAll() {
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        List<Student> studentList = studentService.findAll();
        assertEquals(students, studentList);
    }

    @Test
    public void findByRollStudent() {
        Student student = new Student(3, "1604-16-733-030", "Anand",
                "Shabad", "adil@gmail.com","Semester_1","Due");
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        assertEquals(studentService.findById(3), student);
    }

    @Test
    public void save() {
        Student student = new Student(3, "1604-16-733-030", "Anand",
                "Shabad", "adil@gmail.com","Semester_1","Due");
        studentService.save(student);
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }

    @Test
    public void delete() {
        Student student = new Student(3, "1604-16-733-030", "Anand",
                "Shabad", "adil@gmail.com","Semester_1","Due");
        studentService.deleteById(student.getId());
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(student.getId());
    }

}
