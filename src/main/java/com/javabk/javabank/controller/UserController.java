package com.javabk.javabank.controller;

import com.javabk.javabank.DTO.BankResponse;
import com.javabk.javabank.DTO.UserRequest;
import com.javabk.javabank.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/post/")

public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public BankResponse  createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }
}
