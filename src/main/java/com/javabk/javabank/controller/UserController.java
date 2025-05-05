package com.javabk.javabank.controller;

import com.javabk.javabank.DTO.BankResponse;
import com.javabk.javabank.DTO.CreditAndDebit;
import com.javabk.javabank.DTO.EnquiryRequest;
import com.javabk.javabank.DTO.UserRequest;
import com.javabk.javabank.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/")

public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/createAccount")
    public BankResponse  createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }
    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return userService.nameEnquiry(request);
    }

    @PostMapping("/tocreditaccount")
    public BankResponse creditAccount(@RequestBody CreditAndDebit Request){
        return userService.creditAccount(Request);
    }


}
