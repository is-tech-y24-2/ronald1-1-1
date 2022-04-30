package ru.itmo.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.main.dao.model.Role;
import ru.itmo.main.service.UserService;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public ResponseEntity getUsers(){
        try {
            return new ResponseEntity(userService.getAllUsers(), HttpStatus.ACCEPTED);
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
            userService.saveUser(username, password, role);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
