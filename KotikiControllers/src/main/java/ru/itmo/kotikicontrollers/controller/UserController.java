package ru.itmo.kotikicontrollers.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itmo.kotikicontrollers.kafka.KafkaUtil;


@Controller
@RequestMapping(path = "/auth")
public class UserController {

    @Autowired
    public KafkaUtil kafkaUtil;

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    private ResponseEntity loginGet(){
                return ResponseEntity.ok("Log in please!");
    }



}
