package com.ms.user.producers;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserProducer {

     @Autowired
    final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel){
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Cadastro Realizado com Sucesso!");
        emailDto.setText(userModel.getName() + ", seja Bem-víndo(a)! \n Agradecemos o seu cadastro, aproveite todos os recursos de nossa plataforma!");

       rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

}
