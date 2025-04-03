package com.javabk.javabank.service.impl;

import com.javabk.javabank.DTO.BankResponse;
import com.javabk.javabank.DTO.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
