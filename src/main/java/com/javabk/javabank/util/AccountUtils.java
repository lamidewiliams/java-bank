package com.javabk.javabank.util;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_CODE="0001";
    public static final String ACCOUNT_EXIST_MESSAGE="This user already exist!!";
    public static final String ACCOUNT_CREATED_SUCCESS_CODE ="0002";
    public static final String ACCOUNT_CREATED_SUCCESS_MESSAGE ="Account created successfully!!";


    public  static String generateAccountNumber() {
        /*2023 + any random 6 digit*/
        Year currentYear = Year.now();
        int min =100000;
        int max = 999999;
        /*now we generate randoms */
        int randomNumber = (int)Math.floor(Math.random() * (max-min -1) + max);
        String year = String.valueOf(currentYear);
        String randNumber =String.valueOf(randomNumber);
        StringBuilder accountNumber = new StringBuilder(year);
        return accountNumber.append(year).append(randNumber).toString();


    }
}
