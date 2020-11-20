package com.arshil.springbootdemo.studentfee.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordChecker {
    String roll;
    String oldPassword;
    String oldPasswordChecker;
    String newPassword;
    String newPasswordChecker;
}
