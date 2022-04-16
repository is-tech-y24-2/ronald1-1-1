package ru.itmo.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.main.service.OwnerService;
import ru.itmo.main.tool.Util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    private Logger logger = LoggerFactory.getLogger(OwnerController.class);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity postOwner(@RequestParam String name,
                                    @RequestParam String birthday){
        try {
            ownerService.saveOwner(name, new Date(new SimpleDateFormat("yyyyMMdd").parse(birthday).getTime()));
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong parameters!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getOwners(){
        try{
            return new ResponseEntity(
                    ownerService.
                            getAllOwners().
                            stream().
                            map(ownersEntity -> Util.getWrapperFromEntity(ownersEntity)).
                            collect(Collectors.toList()), HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity getOwnerById(@PathVariable int id){
        try {
            return new ResponseEntity(Util.getWrapperFromEntity(ownerService.getOwnerById(id)), HttpStatus.ACCEPTED);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!",HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity deleteOwnerById(@PathVariable int id){
        try {
            ownerService.deleteOwnerById(id);
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!", HttpStatus.BAD_REQUEST);
        }
    }
}