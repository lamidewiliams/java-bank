package com.javabk.javabank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder@AllArgsConstructor@NoArgsConstructor
public class TransferRequest {
    private String SendersAccount; // account that will be debited
    private String ReceiversAccount; // This account will be credited
    private String Amount; // The amount that will be sent
}
