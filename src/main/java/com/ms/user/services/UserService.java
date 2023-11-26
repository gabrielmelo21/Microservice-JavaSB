package com.ms.user.services;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService{

    @Autowired
    private UsersRepository repository;

    @Autowired
    private UserProducer userProducer;


    @Transactional
    public UserModel saveUser(UserModel userModel) throws Exception {
        try {
            if (!verifyEmail(userModel.getEmail())) {
                 userModel =  repository.save(userModel);
                 userProducer.publishMessageEmail(userModel);
                return userModel;
            } else {
                throw new Exception("E-mail já cadastrado");
            }
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o usuário", e);
        }
    }

    public Boolean verifyEmail(String email) {
        List<UserModel> allUsers = repository.findAll();
        return allUsers.stream().anyMatch(user -> user.getEmail().equals(email));
    }


    public ResponseEntity<String> deleteAllUsers(){
        repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
    }

}
