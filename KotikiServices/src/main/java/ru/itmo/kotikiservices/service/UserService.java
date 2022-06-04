package ru.itmo.kotikiservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotikiservices.dao.model.Role;
import ru.itmo.kotikiservices.dao.model.UserEntity;
import ru.itmo.kotikiservices.dao.repository.UserRepository;


import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;



    public UserEntity loadUserByUsername(String username){

        UserEntity userDetails = userRepository.findByUserName(username);
        return userDetails;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUserById(int id){
        return userRepository.getById(id);
    }

    public UserEntity saveUser(String username, String password, Role role){
        UserEntity user = new UserEntity();
        user.setUserName(username);
        user.setUserPassword(password);
        user.setUserRole(role);

        userRepository.save(user);
        return user;
    }
}
