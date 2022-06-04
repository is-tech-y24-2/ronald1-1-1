package ru.itmo.kotikicontrollers.controller;

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
import ru.itmo.kotikicontrollers.kafka.KafkaUtil;
import ru.itmo.kotikicontrollers.model.Color;
import ru.itmo.kotikicontrollers.model.Message;
import ru.itmo.kotikicontrollers.wrapper.CatWrapper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/cat")
public class CatController {

    @Autowired
    private KafkaUtil kafkaUtil;

    Logger logger = LoggerFactory.getLogger(CatController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCats(
            @RequestParam(required = false) Color color){
        try {
            boolean result;
            Map<String, String> params = new HashMap<>();
            if (color != null) {
                params.put("color", color.toString());
                result = kafkaUtil.sendMessage("cat.getByColor", params);

            } else {
                result = kafkaUtil.sendMessage("cat.getAll", params);
            }
            if(result) {
                return new ResponseEntity("success", HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity getCatById(@PathVariable int id){
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", Integer.toString(id));
            boolean result = kafkaUtil.sendMessage("cat.get", params);
            if(result) return new ResponseEntity("success", HttpStatus.ACCEPTED);
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!",HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity postCat(@RequestParam String name,
                                  @RequestParam String breed,
                                  @RequestParam Color color,
                                  @RequestParam int owner,
                                  @RequestParam String birthday) {
        try{
            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("breed", breed);
            params.put("color", color.toString());
            params.put("owner", Integer.toString(owner));
            params.put("birthday", birthday);

            boolean result = kafkaUtil.sendMessage("cat.save", params);

//                    new Date(new SimpleDateFormat("yyyyMMdd").parse(birthday).getTime()
            if (result)return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
            return new ResponseEntity("error", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong parameters!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity deleteCatById(@PathVariable int id){
        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", Integer.toString(id));
            boolean result = kafkaUtil.sendMessage("cat.delete", params);
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/friends/{id}")
    public ResponseEntity getCatFriends(@PathVariable int id){
        try{
            Map<String, String> params = new HashMap<>();
            params.put("id", Integer.toString(id));
            boolean result = kafkaUtil.sendMessage("cat.getFriends", params);
            return new ResponseEntity("success", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/friends")
    public ResponseEntity makeFriends(@RequestParam int firstCat,
                                      @RequestParam int secondCat){
        try{
            Map<String, String> params = new HashMap<>();
            params.put("firstCat", Integer.toString(firstCat));
            params.put("secondCat", Integer.toString(secondCat));
            boolean result = kafkaUtil.sendMessage("cat.makeFriends", params);
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong parameters!", HttpStatus.BAD_REQUEST);
        }
    }


}

