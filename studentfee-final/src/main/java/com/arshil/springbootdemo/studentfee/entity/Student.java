package com.arshil.springbootdemo.studentfee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    @Pattern(regexp = "^1604-[0-9]{2}-[0-9]{3}-[0-9]{3}$", message = "please make sure it is in the form 1604-34-34-34")
    private String roll;
    @Column
    @NotNull
    @Size(min = 1, message = "please don't leave it empty")
    private String firstName;
    @Column
    @NotNull
    @Size(min = 1, message = "please don't leave it empty")
    private String lastName;
    @Column
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.((com)|(org)|(gov)|(co\\.[a-z]+))$", message="please enter a valid email")
    private String email;
    @Column
    @Pattern(regexp = "^Semester_[1-8]$", message="please enter sems 1-4")
    private String semester;
    @Column
    @Pattern(regexp = "((Due)|(No_Due))", message="please enter due or no due")
    private String feeDue;

    public Student() {

    }

    public Student(int id,
                   @Pattern(regexp = "^1604-[0-9]{2}-[0-9]{3}-[0-9]{3}$",
                           message = "please make sure it is in the form 1604-34-34-34")
                           String roll,
                   @NotNull @Size(min = 1,
                           message = "please don't leave it empty")
                           String firstName,
                   @NotNull @Size(min = 1,
                           message = "please don't leave it empty")
                           String lastName,
                   @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.((com)|(org)|" +
                           "(gov)|(co\\.[a-z]+))$",
                           message = "please enter a valid email")
                           String email,
                   @Pattern(regexp = "^Semester_[1-8]$",
                           message = "please enter sems 1-4") String semester,
                   @Pattern(regexp = "((Due)|(No_Due))",
                           message = "please enter due or no due")
                           String feeDue) {
        this.id = id;
        this.roll = roll;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.semester = semester;
        this.feeDue = feeDue;
    }
}
