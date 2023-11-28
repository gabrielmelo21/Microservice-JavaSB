package com.email.ms.consumer;

import com.email.ms.dtos.EmailRecordDTO;
import com.email.ms.models.EmailModel;
import com.email.ms.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


    @Component
    @AllArgsConstructor
    public class EmailConsumerTeste {

        @Autowired
        final private EmailService emailService;


      //  @RabbitListener(queues = "${broker.queue.email.name}")
        public void listenEmailQueue(@Payload MessageWrapper messageWrapper){

            try {
                System.out.println("Dados do MW: " + messageWrapper.getHtmlSettings().getBackgroundImage());
                System.out.println("Dados do MW: " + messageWrapper.getHtmlSettings().getLogoURL());
                System.out.println("Dados do MW: " + messageWrapper.getHtmlSettings().getTitle());


                System.out.println("Dados do Email: " + messageWrapper.getEmail().getUserId());
                System.out.println("Dados do Email: " + messageWrapper.getEmail().getEmailTo());
                System.out.println("Dados do Email: " + messageWrapper.getEmail().getSubject());
                System.out.println("Dados do Email: " + messageWrapper.getEmail().getText());

            }catch (Exception e){
                System.out.println(e.getMessage());
            }




        }
    }


// var emailModel = new EmailModel();
// BeanUtils.copyProperties(emailRecordDTO, emailModel);



//emailService.sendEmail(emailModel);

//  System.out.println(emailRecordDTO.emailTo());