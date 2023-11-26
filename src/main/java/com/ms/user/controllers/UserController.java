package com.ms.user.controllers;

import com.ms.user.dtos.UserRecordDTO;
import com.ms.user.models.UserModel;
import com.ms.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/users")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDTO userRecordDTO) throws Exception {
         var userModel = new UserModel();

        BeanUtils.copyProperties(userRecordDTO, userModel);



        return  ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(userModel));
    }


    @DeleteMapping
    public ResponseEntity<String> deleteAll(){
        return  service.deleteAllUsers();
    }
}
