package com.javabk.javabank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Service
public class EmailDetails {
    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;
}
