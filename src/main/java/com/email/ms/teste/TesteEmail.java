package com.email.ms.teste;

import com.email.ms.consumer.HTMLSettings;
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

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            /**
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo("gabriel.user0004@gmail.com");
            message.setSubject("forcing email send");
            message.setText("forcing email send txt");

             **/

            helper.setFrom(emailFrom);
            helper.setTo("gabriel.user0001@gmail.com");
            helper.setSubject("Email com HTML");

            HTMLSettings settings = new HTMLSettings(
                    "https://img.freepik.com/free-vector/geometric-abstract-green-background_23-2148380710.jpg?size=626&ext=jpg&ga=GA1.1.1880011253.1699833600&semt=ais",
                    "https://content-assets.unum.com.br/wp-content/2020/04/ppay-icon.png",
                    "#FFF",
                    "#FFF",
                    "Você recebeu dinheiro na sua Conta! (Agora com Objeto)",
                    "João enviou R$50.00 para sua conta " + LocalDateTime.now(),
                    "<button style='border-radius:20px;border:none;background:#fff;color:black;width:30%;padding:10px;margin:auto;'>Ver no App</button>"
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


        } catch (MailException e) {
            System.out.println(e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
