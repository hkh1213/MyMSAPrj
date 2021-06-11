package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "Email cannot be Null")
    @Size(min = 2,message = "Email not be less than 2 Chr")
    @Email
    private String email;

    @NotNull(message = "name cannot be Null")
    @Size(min = 2,message = "name not be less than 2 Chr")
    private String name;

    @NotNull(message = "pwd cannot be Null")
    @Size(min = 8,message = "pwd not be less than 8 Chr")
    private String pwd;

}
