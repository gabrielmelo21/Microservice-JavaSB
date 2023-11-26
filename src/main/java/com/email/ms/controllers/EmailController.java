package com.email.ms.controllers;

import com.email.ms.models.EmailModel;
import com.email.ms.services.EmailService;
import com.email.ms.teste.TesteEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TesteEmail testeEmail;

    @GetMapping
    public List<EmailModel> emailModelList(){
        return emailService.getAllSendedEmails();
 }

 @GetMapping("/teste")
    public void teste(){
         testeEmail.sendForcingEmail();
 }



}
