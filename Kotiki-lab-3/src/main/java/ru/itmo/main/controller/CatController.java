package ru.itmo.main.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.itmo.main.dao.model.CatsEntity;
import ru.itmo.main.dao.model.Color;
import ru.itmo.main.tool.Util;
import ru.itmo.main.wrapper.CatWrapper;
import ru.itmo.main.service.CatService;
import ru.itmo.main.service.OwnerService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/cat")
public class CatController {

    @Autowired
    private CatService catService;

    @Autowired
    private OwnerService ownerService;

    Logger logger = LoggerFactory.getLogger(CatController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCats(
            @RequestParam(required = false) Color color){
        System.out.println("sdfsdfsdf");
        try {
            List<CatsEntity> catsEntities;
            if (color != null) {
                catsEntities = catService.getAllCatsByColor(color);
            } else {
                catsEntities = catService.getAllCats();
            }
            return new ResponseEntity(catsEntities.
                    stream().
                    map((catsEntity -> Util.getWrapperFromEntity(catsEntity))).
                    collect(Collectors.toList()), HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity getCatById(@PathVariable int id){
        try {
            return new ResponseEntity(Util.getWrapperFromEntity(catService.getCatById(id)), HttpStatus.ACCEPTED);
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
            catService.saveCat(name,
                    breed,
                    color,
                    ownerService.getOwnerById(owner),
                    new Date(new SimpleDateFormat("yyyyMMdd").parse(birthday).getTime()));
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong parameters!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity deleteCatById(@PathVariable int id){
        try {
            catService.deleteCatById(id);
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/friends/{id}")
    public ResponseEntity getCatFriends(@PathVariable int id){
        try{
            return new ResponseEntity(catService.
                    getFriends(id).
                    stream().
                    map((catsEntity -> Util.getWrapperFromEntity(catsEntity))).
                    collect(Collectors.toList()), HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong id!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/friends")
    public ResponseEntity makeFriends(@RequestParam int firstCat,
                                      @RequestParam int secondCat){
        try{
            catService.makeFriends(firstCat, secondCat);
            return new ResponseEntity("Success!", HttpStatus.ACCEPTED);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity("Wrong parameters!", HttpStatus.BAD_REQUEST);
        }
    }


}

