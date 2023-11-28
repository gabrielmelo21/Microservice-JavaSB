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

    /**
     * Aqui recebemos os dados do RabbitMQ
     * MessageWrapper contem dois objetos, o Email e o HtmlSettings que são configurações do html do email
     *
     * @param messageWrapper
     */


    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload MessageWrapper messageWrapper){
        emailService.sendEmail(messageWrapper);
    }

    }
