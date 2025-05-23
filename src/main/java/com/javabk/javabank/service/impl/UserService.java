package com.javabk.javabank.service.impl;

import com.javabk.javabank.DTO.BankResponse;
import com.javabk.javabank.DTO.CreditAndDebit;
import com.javabk.javabank.DTO.EnquiryRequest;
import com.javabk.javabank.DTO.UserRequest;



public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
     // String nameInquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditAndDebit request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse debitAccount(CreditAndDebit request);
}
