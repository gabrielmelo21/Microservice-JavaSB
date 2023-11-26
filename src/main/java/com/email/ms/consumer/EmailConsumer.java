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
public class EmailConsumer {

      @Autowired
     final private EmailService emailService;


    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDTO emailRecordDTO){

         var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailRecordDTO, emailModel);

        emailService.sendEmail(emailModel);

      //  System.out.println(emailRecordDTO.emailTo());
    }
}
