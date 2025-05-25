package com.javabk.javabank.service.impl;

import com.javabk.javabank.DTO.*;
import com.javabk.javabank.entity.User;
import com.javabk.javabank.repository.UserRepository;
import com.javabk.javabank.util.AccountUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.CacheRequest;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        // just to check if a user or particular number has registered
        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            return BankResponse.builder()
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
                .phoneNumber(userRequest.getPhoneNumber())
                .alternatePhoneNumber(userRequest.getAlternatePhoneNumber())
                .status("ACTIVE")
                .sentEmail(false)
                .build();
        User savedUser = userRepository.save(newuser);
        /*creating account will add new user , post method*/

        //**********************************************************************

        sendEmail(savedUser);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATED_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .build())
                .build();
    }

    //************************************************************************************
    private void sendEmail(User savedUser) {
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation ")
                .messageBody("Hello Your Account has Been Created Successfully!" +
                        "\nYour account details: \n " +
                        "Account name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\n" +
                        "Account number: " + savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);


    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean ifAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!ifAccountExists){
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                .accountInfo(null)
                .build();
        }User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUNND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .accountName(foundUser.getFirstName()+ " "+ foundUser.getLastName() + " " + foundUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean ifAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!ifAccountExists){
            return AccountUtils.ACCOUNT_NOT_EXIST_CODE;
        }User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }



    @Transactional
    @Override
    public BankResponse creditAccount(CreditAndDebit request) {
        boolean ifAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!ifAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
                }
        User usercredit = userRepository.findByAccountNumber(request.getAccountNumber());
        usercredit.setAccountBalance(usercredit.getAccountBalance().add(request.getAmount()));
       // userRepository.save(usercredit);

        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_Credited_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_Credited_code)
                .accountInfo(AccountInfo.builder()
                        .accountName(usercredit.getFirstName()+ " "+usercredit.getLastName()+" "+usercredit.getOtherName())
                        .accountBalance(usercredit.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .build()
                )
                .build();
    }
    @Transactional
    @Override
    public BankResponse debitAccount(CreditAndDebit request) {
        boolean ifAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!ifAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userdebit = userRepository.findByAccountNumber(request.getAccountNumber());
        BigInteger availablBalance = userdebit.getAccountBalance().toBigInteger();
        BigInteger amountTodebit = request.getAmount().toBigInteger();
        if (availablBalance.compareTo(amountTodebit)<0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.BALANCE_LOW_CODE)
                    .responseMessage(AccountUtils.BALANCE_LOW_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        else{
            userdebit.setAccountBalance(userdebit.getAccountBalance().subtract(request.getAmount()));
            return BankResponse.builder()
                    .responseCode(AccountUtils.Debit_code)
                    .responseMessage(AccountUtils.DEBIT_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountName(userdebit.getFirstName()+" "+userdebit.getLastName())
                            .accountBalance(userdebit.getAccountBalance())
                            .build())
                    .build();
        }

    }
    public  BankResponse transfer(TransferRequest request){
        boolean ifSendersAccountExists = userRepository.existsByAccountNumber(request.getSendersAccount());
        boolean ifReceiversAccountExists = userRepository.existsByAccountNumber(request.getReceiversAccount());
        if (!ifSendersAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.SENDERS_ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.SENDERS_ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        if (!ifReceiversAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.Receiver_ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.Receiver_ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

    }


}
