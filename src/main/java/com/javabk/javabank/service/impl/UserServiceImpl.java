package com.javabk.javabank.service.impl;

import com.javabk.javabank.DTO.AccountInfo;
import com.javabk.javabank.DTO.BankResponse;
import com.javabk.javabank.DTO.UserRequest;
import com.javabk.javabank.entity.User;
import com.javabk.javabank.repository.UserRepository;
import com.javabk.javabank.util.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            return  BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .accountInfo(null)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .build();

        }



        User newuser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .phoneNumber(userRequest.getPhoneNumber())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .email(userRequest.getEmail())
                .accountBalance(BigDecimal.ZERO)
                .alternatePhoneNumber(userRequest.getPhoneNumber())
                .alternatePhoneNumber(userRequest.getAlternatePhoneNumber())
                .status("ACTIVE")
                .build();
        /*creating account will add new user , post method*/

    User savedUser = userRepository.save(newuser);
    return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_CREATED_SUCCESS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_CREATED_SUCCESS_MESSAGE)
            .accountInfo(AccountInfo.builder()
                    .accountBalance(savedUser.getAccountBalance())
                    .accountNumber(savedUser.getAccountNumber())
                    .accountName(savedUser.getFirstName()+" "+savedUser.getLastName())
                    .build())


            .build();
    }

}
