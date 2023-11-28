package com.email.ms.services;

import com.email.ms.consumer.HTMLSettings;
import com.email.ms.consumer.MessageWrapper;
import com.email.ms.enums.StatusEmail;
import com.email.ms.models.EmailModel;
import com.email.ms.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    /**
     *  Aqui salvamos o email que foi enviado, sendo SENT ou ERROR
     *  recebemos o objetos de MessageWrapper que contem o Email e o HtmlSettings vindos de algum MS nosso
     *  com esses dados criamos um email com os dados de email e com as configurações html passadas pelo ms especifico
     *
     *  (Todos microservice que  usar em ms para enviar emails, deve conter as classes HTMLSettings, Email e MessageWrapper)
     *  onde definem a messagem do email, para quem, e o estilo geral desse email.
     */


    @Transactional
    public EmailModel sendEmail(MessageWrapper messageWrapper){



            EmailModel emailModel = new EmailModel();


        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);


            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setFrom(emailFrom);
            helper.setTo(messageWrapper.getEmail().getEmailTo());
            helper.setSubject(messageWrapper.getEmail().getSubject());

            HTMLSettings settings = new HTMLSettings(
                    messageWrapper.getHtmlSettings().getBackgroundImage(),
                    messageWrapper.getHtmlSettings().getLogoURL(),
                    messageWrapper.getHtmlSettings().getTitleColor(),
                    messageWrapper.getHtmlSettings().getTxtColor(),
                    messageWrapper.getHtmlSettings().getTitle(),
                    messageWrapper.getHtmlSettings().getTxt(),
                    messageWrapper.getHtmlSettings().getActionButton()
            );


            String htmlContent = "<html><body style='background-image: url(\" " + settings.getBackgroundImage() + " \");height:500px;" +
                    " background-size: cover; text-align: center; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;'>" +
                    "<img src='" + settings.getLogoURL() + "' alt='Logo' style='max-width: 70px; height: auto;margin-top:20px;'>" +
                    "<h1 style='color: " + settings.getTitleColor() + ";'>" + settings.getTitle() + "</h1>" +
                    "<p style='font-size: 18pt;color:" + settings.getTxtColor() + ";'>" + settings.getTxt() + "</p>" +
                    settings.getActionButton() +
                    "</body></html>";



            helper.setText(htmlContent, true);


            javaMailSender.send(mimeMessage);


            emailModel.setStatusEmail(StatusEmail.SENT);
            System.out.println("Email eviado com sucesso");
            return emailRepository.save(emailModel);




        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            System.out.println("Erro ao enviar email.");
            return emailRepository.save(emailModel);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }



    }




    public List<EmailModel> getAllSendedEmails(){
         return emailRepository.findAll();
    }
}
