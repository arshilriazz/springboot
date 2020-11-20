package com.arshil.springbootdemo.studentfee.controller;

import com.arshil.springbootdemo.studentfee.entity.PaymentDetails;
import com.arshil.springbootdemo.studentfee.entity.Student;
import com.arshil.springbootdemo.studentfee.entity.User;
import com.arshil.springbootdemo.studentfee.service.PasswordChecker;
import com.arshil.springbootdemo.studentfee.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/students")
@Slf4j
public class StudentController {
    @Autowired
    StudentService studentService;

    public String passInBcrypt(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(10));
    }

    @GetMapping("/list")
    public String studentList(Model model, HttpServletRequest httpServletRequest) {
        List<Student> students = null;
        String username = httpServletRequest.getUserPrincipal().getName();
        boolean isAdmin = httpServletRequest.isUserInRole("ADMIN");
        if(isAdmin) {
            students = studentService.findAll();
        }
        else {
            students = studentService.findByRollInStudents(username);
        }
        model.addAttribute("roll", username);
        log.info("sending roll number");
        model.addAttribute("students", students);
        log.info("sending the list of students");
        return "list-students";
    }
    
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Student student = new Student();
        String show = "show";
        theModel.addAttribute("show", show);
        theModel.addAttribute("student", student);
        log.info("form to add a student");
        return "student-form-for-adding";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("studentId") int theId, Model model) {
        Student student = studentService.findById(theId);
        if(student == null) {
            return "error-page";
        }
        String show = "show";
        model.addAttribute("show", show);
        model.addAttribute("student", student);
        log.info("form to display a student");
        return "student-form";
    }

    @GetMapping("/payTheFee")
    public String payTheFee(@RequestParam("studentId") int theId) {
        Student student = studentService.findById(theId);
        student.setFeeDue("No_Due");
        studentService.save(student);
        log.info("if this function is called your system is broken");
        return "redirect:/students/list";
    }

    @PostMapping("/save")
    public String updateStudent(@Valid @ModelAttribute("student") Student student,
                                BindingResult bindingResult,
                                Model model) {
        if(bindingResult.hasErrors()) {
            log.info("validation errors");
            return "student-form";
        }
        List<Student> students;
        students = studentService.findByRollInStudents(student.getRoll());
        for(Student kid : students) {
            String semester = student.getSemester();
            String semester1 = kid.getSemester();
            if(semester.equals(semester1) && student.getId() != kid.getId()) {
                log.info("should not add same semester");
                String show = "same";
                model.addAttribute("show", show);
                return "student-form";
            }
        }
        studentService.save(student);
        return "redirect:/students/list";
    }

    @PostMapping("/save1")
    public String saveStudentInDatabase(@Valid @ModelAttribute("student") Student student,
                                        BindingResult bindingResult,
                                        Model model) {
        if(bindingResult.hasErrors()) {
            log.info("validation errors");
            return "student-form";
        }
        List<Student> students = null;
        User user1 = studentService.findByRoll(student.getRoll());
        User user = new User();
        if(user1 == null) {
            user.setRole("ROLE_USER");
            user.setPassword(passInBcrypt("1234"));
            user.setUsername(student.getRoll());
            log.info("new user is added");
            studentService.save(user);
        }
        else {
            students = studentService.findByRollInStudents(student.getRoll());
            for(Student kid : students) {
                String fee1 = student.getSemester();
                String fee2 = kid.getSemester();
                if(fee1.equals(fee2)) {
                    log.info("should not add same semester");
                    String show = "same";
                    model.addAttribute("show", show);
                    return "student-form";
                }
            }
        }
        studentService.save(student);
        return "redirect:/students/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("studentId") int id) {
        studentService.deleteById(id);
        log.info("student is deleted");
        return "redirect:/students/list";
    }

    @GetMapping("/changePasswordForm")
    public String changePasswordForm(@RequestParam("studentRoll") String roll, Model model) {
        User user = studentService.findByRoll(roll);
        if(user == null) {
            return "error-page";
        }
        String password = user.getPassword();
        PasswordChecker passwordChecker = new PasswordChecker();
        passwordChecker.setRoll(roll);
        passwordChecker.setOldPassword(password);
        model.addAttribute("roll", roll);
        model.addAttribute("checker", passwordChecker);
        model.addAttribute("user", user);
        return "change-password";
    }

    public boolean containsLetter(char letter, String string) {
        for (char i : string.toCharArray()) {
            if (i == letter) {
                return true;
            }
        }
        return false;
    }

    public boolean passwordCheck(String password) {
        int countLetterUpper = 0;
        int countLetterLower = 0;
        int countNumber = 0;
        String upperLetters = "ABCDEFGHIJKMNOPQRSTUVWXYZ";
        String lowerLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "1234567890";
        for (char letter : password.toCharArray()) {
            if (containsLetter(letter, upperLetters)) {
                countLetterUpper++;
            }
            if(containsLetter(letter, lowerLetters)) {
                countLetterLower++;
            }
            if(containsLetter(letter, numbers)) {
                countNumber++;
            }
        }
        if(countLetterLower > 0 && countLetterUpper > 0 && countNumber > 0) return true;
        return false;
    }

    @PostMapping("/savePassword")
    public String savePassword(@ModelAttribute("checker") PasswordChecker passwordChecker,
                               @RequestParam("studentRoll") String roll,
                               Model model) {
        User user1 = null;
        String newPassword = passwordChecker.getNewPassword();
        String newPasswordChecker = passwordChecker.getNewPasswordChecker();
        if(newPassword.length() < 6) {
            model.addAttribute("roll", roll);
            passwordChecker.setOldPassword("length");
            log.info("length of password is less than or equal to 6");
            return "change-password";
        }
        if(!passwordCheck(newPassword)) {
            model.addAttribute("roll", roll);
            passwordChecker.setOldPassword("yes");
            log.info("not matching the required type");
            return "change-password";
        }
        if(!newPassword.equals(newPasswordChecker)) {
            model.addAttribute("roll", roll);
            passwordChecker.setOldPassword("same");
            log.info("passwords are not matching");
            return "change-password";
        }
        user1 = studentService.findByRoll(roll);
        user1.setPassword(passInBcrypt(newPassword));
        studentService.save(user1);
        return "redirect:/students/list";
    }

    @GetMapping("/search")
    public String delete(@RequestParam("rollNumber") String roll, Model model) {
        List<Student> students = studentService.findByRollInStudents(roll);
        if(students == null) {
            log.info("no proper name");
            return "error-page";
        }
        model.addAttribute("students", students);
        return "list-students-roll";
    }
    @GetMapping("/paymentForm")
    public String payment(@RequestParam("studentId") int id, Model model) {
        PaymentDetails paymentDetails = new PaymentDetails();
        Student student = studentService.findById(id);
        paymentDetails.setRoll(student.getRoll());
        paymentDetails.setId(id);
        model.addAttribute("idNumber", student.getRoll());
        model.addAttribute("paymentDetails", paymentDetails);
        return "payment-form";
    }

    @PostMapping("/paymentConfirmation")
    public String paymentConfirmation(@RequestParam("studentId") int id,
                                      @Valid @ModelAttribute("paymentDetails")
                                              PaymentDetails paymentDetails,
                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("this has errors");
            return "payment-form";
        }
        Student student = studentService.findById(id);
        if(student == null) {
            return "error-page";
        }
        student.setFeeDue("No_Due");
        studentService.save(student);
        return "redirect:/students/list";
    }
}