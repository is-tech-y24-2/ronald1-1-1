package ru.itmo.kotikicontrollers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.kotikicontrollers.kafka.KafkaUtil;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/owner")
public class OwnerController {

    @Autowired
    private KafkaUtil kafkaUtil;

    private Logger logger = LoggerFactory.getLogger(OwnerController.class);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity postOwner(@RequestParam String name,
                                    @RequestParam String birthday){
        try {
            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("birthday", birthday);
            params.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
            boolean result = kafkaUtil.sendMessage("owner.save", params);
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong parameters!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getOwners(){
        try{
            Map<String, String> params = new HashMap<>();
            boolean result = kafkaUtil.sendMessage("owner.getAll", params);
            return new ResponseEntity("success", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity getOwnerById(@PathVariable int id){
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", Integer.toString(id));
            boolean result = kafkaUtil.sendMessage("owner.get", params);
            return new ResponseEntity("success", HttpStatus.ACCEPTED);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!",HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity deleteOwnerById(@PathVariable int id){
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", Integer.toString(id));
            boolean result = kafkaUtil.sendMessage("owner.delete", params);
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!", HttpStatus.BAD_REQUEST);
        }
    }
}