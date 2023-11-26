package com.email.ms.teste;

import com.email.ms.enums.StatusEmail;
import com.email.ms.models.EmailModel;
import com.email.ms.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Data
public class TesteEmail {

    @Autowired
    final EmailRepository emailRepository;

    @Autowired
    final JavaMailSender javaMailSender;


    @Value(value = "${spring.mail.username}")
    private String emailFrom;



    @Transactional
    public void sendForcingEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo("gabriel.user0004@gmail.com");
            message.setSubject("forcing email send");
            message.setText("forcing email send txt");
            javaMailSender.send(message);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }


}
