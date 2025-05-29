package com.javabk.javabank.util;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_CODE="0001";
    public static final String ACCOUNT_EXIST_MESSAGE="This user already exist!!";

    public static final String ACCOUNT_CREATED_SUCCESS_CODE ="0002";
    public static final String ACCOUNT_CREATED_SUCCESS_MESSAGE ="Account created successfully!!";

    public static final String ACCOUNT_NOT_EXIST_CODE ="0003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE ="User does not exist!!";

    public static final String ACCOUNT_FOUND_CODE ="0004";
    public static final String ACCOUNT_FOUNND_MESSAGE ="USER FOUND";

    public static final String ACCOUNT_Credited_code ="0005";
    public static final String ACCOUNT_Credited_MESSAGE ="Account credited successfully!!";

    public static final String BALANCE_LOW_CODE ="0005";
    public static final String BALANCE_LOW_MESSAGE ="Insufficient balance!!";

    public static final String Debit_code ="0006";
    public static final String DEBIT_MESSAGE ="Your account has been debited";

    // for transfers
    public static final String TRANSFER_SUCCESSFULL_CODE ="0008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE ="Transfer successful!! ";

    public static final String Receiver_ACCOUNT_NOT_EXIST_CODE ="0007";
    public static final String Receiver_ACCOUNT_NOT_EXIST_MESSAGE ="Destination account does not exist or temporarily unavailable !!";

    public static final String Senders_ACCOUNT_NOT_EXIST_CODE ="0009";


    public  static String generateAccountNumber() {
        /*2023 + any random 6 digit*/
        Year currentYear = Year.now();
        int min =100000;
        int max = 999999;
        /*now we generate randoms */
        int randomNumber = (int)Math.floor(Math.random() * (max-min -1) + min);
        String year = String.valueOf(currentYear);
        String randNumber =String.valueOf(randomNumber);
        StringBuilder accountNumber = new StringBuilder(year);
        return accountNumber.append(year).append(randNumber).toString();


    }
}
