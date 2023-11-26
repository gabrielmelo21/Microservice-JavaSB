package com.email.ms.services;

import com.email.ms.enums.StatusEmail;
import com.email.ms.models.EmailModel;
import com.email.ms.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
public class EmailService {
    @Autowired
    final EmailRepository emailRepository;

    @Autowired
    final JavaMailSender javaMailSender;


    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel){

             try {
                 emailModel.setSendDateEmail(LocalDateTime.now());
                 emailModel.setEmailFrom(emailFrom);
                 SimpleMailMessage message = new SimpleMailMessage();
                 message.setFrom(emailFrom);
                 message.setTo(emailModel.getEmailTo());
                 message.setSubject(emailModel.getSubject());
                 message.setText(emailModel.getText());
                 javaMailSender.send(message);
                 emailModel.setStatusEmail(StatusEmail.SENT);
                 return emailRepository.save(emailModel);
             }catch (MailException e) {
                 emailModel.setStatusEmail(StatusEmail.ERROR);
                 return emailRepository.save(emailModel);
             }

    }




    public List<EmailModel> getAllSendedEmails(){
         return emailRepository.findAll();
    }
}
