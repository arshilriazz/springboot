package com.arshil.springbootdemo.studentfee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class PaymentDetails {
    int id;
    @Pattern(regexp = "^1604-[0-9]{2}-[0-9]{3}-[0-9]{3}$", message = "please make sure it is in the form 1604-34-34-34")
    String roll;
    @Pattern(regexp = "^[0-9]{16}$", message = "please make sure it is 16 digits")
    String cardNumber;
    @Pattern(regexp = "^[0-9]{3}$",  message = "please make sure it is 3 digits")
    String cvv;
}
