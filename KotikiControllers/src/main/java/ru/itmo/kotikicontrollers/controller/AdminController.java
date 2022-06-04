package ru.itmo.kotikicontrollers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotikicontrollers.kafka.KafkaUtil;
import ru.itmo.kotikicontrollers.model.Message;
import ru.itmo.kotikicontrollers.model.Role;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private KafkaUtil kafkaUtil;

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public ResponseEntity getUsers(){
        try {
            Map<String, String> params = new HashMap<>();
            boolean result = kafkaUtil.sendMessage("user.getAll", params);
            if(result) {
                return new ResponseEntity("success", HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user")
    public ResponseEntity postUser(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam Role role){
        try{
            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            params.put("role", role.toString());
            boolean result = kafkaUtil.sendMessage("user.getAll", params);
            if (result) return ResponseEntity.ok().build();
            return ResponseEntity.badRequest().build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
