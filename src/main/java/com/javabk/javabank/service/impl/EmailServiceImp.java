package com.javabk.javabank.service.impl;

import com.javabk.javabank.DTO.EmailDetails;
import com.javabk.javabank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp  implements EmailService{
    @Autowired
    private JavaMailSender javamailSender;
    /*@Autowired
    private EmailDetails emailDetails;*/
    @Value("${spring.mail.username}")
    private String senderEmail;

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try {
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient()); // why not use savedUser.getEmail()
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessageBody());
            javamailSender.send(mailMessage);
            System.out.println("mail Sent Successfully!!");
        }catch(MailException e){
            throw new RuntimeException(e);
        }


    }


























        }




