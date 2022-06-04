package ru.itmo.kotikiservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.kotikiservices.dao.model.CatsEntity;
import ru.itmo.kotikiservices.dao.model.Color;
import ru.itmo.kotikiservices.dao.model.FriendsOfCatsEntity;
import ru.itmo.kotikiservices.dao.model.OwnersEntity;
import ru.itmo.kotikiservices.dao.repository.CatRepository;
import ru.itmo.kotikiservices.dao.repository.FriendRepository;
import ru.itmo.kotikiservices.dao.repository.OwnerRepository;
import ru.itmo.kotikiservices.dao.repository.UserRepository;
import ru.itmo.kotikiservices.tool.Util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    public CatsEntity saveCat(String name,
                              String breed,
                              Color color,
                              OwnersEntity owner,
                              Date birthday) throws Exception {
        CatsEntity catsEntity = new CatsEntity();
        catsEntity.setName(name);
        catsEntity.setBreed(breed);
        catsEntity.setColor(color);
        catsEntity.setOwner(owner);
        catsEntity.setBirthday(birthday);
        catRepository.save(catsEntity);
        return catsEntity;
    }

    public List<CatsEntity> getAllCats(){
        return catRepository.findAll();
    }

    public List<CatsEntity> getAllCatsByColor(Color color){
        return catRepository.findAllByColor(color);
    }

    public CatsEntity getCatById(int id) throws Exception {
        CatsEntity catsEntity = catRepository.findById(id);
        return catsEntity;
    }

    public void deleteCat(CatsEntity catsEntity){
         catRepository.delete(catsEntity);
    }

    public void deleteCatById(int id){
        catRepository.deleteById(id);
    }

    public void updateCat(CatsEntity catsEntity){
        catRepository.saveAndFlush(catsEntity);
    }

    public List<CatsEntity> getFriends(int id) throws Exception {
        return getCatById(id).getFriends();
    }

    public void makeFriends(int cat1, int cat2) throws Exception {
        if(getFriends(cat1).stream().anyMatch(cat -> cat.getId() == cat2)){
            return;
        }
        FriendsOfCatsEntity friendsOfCatsEntity1 = new FriendsOfCatsEntity();
        friendsOfCatsEntity1.setFirstCat(cat1);
        friendsOfCatsEntity1.setSecondCat(cat2);
        friendRepository.save(friendsOfCatsEntity1);
        FriendsOfCatsEntity friendsOfCatsEntity2 = new FriendsOfCatsEntity();
        friendsOfCatsEntity2.setFirstCat(cat2);
        friendsOfCatsEntity2.setSecondCat(cat1);
        friendRepository.save(friendsOfCatsEntity2);

    }

    @KafkaListener(id = "getCatByColor", topics = {"cat.getByColor"}, containerFactory = "singleFactory")
    public void getByColor(String object) {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        System.out.println(
                getAllCatsByColor(Color.valueOf(params.get("color")))
        );
    }

    @KafkaListener(id = "getCatById", topics = {"cat.get"}, containerFactory = "singleFactory")
    public void getById(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        System.out.println(
                getCatById(Integer.parseInt(params.get("id")))
        );
    }

    @KafkaListener(id = "saveCat", topics = {"cat.save"}, containerFactory = "singleFactory")
    public void save(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        saveCat(
                params.get("name"),
                params.get("breed"),
                Color.valueOf(params.get("color")),
                ownerRepository.getById(Integer.parseInt(params.get("owner"))),
                new Date(new SimpleDateFormat("yyyyMMdd").parse(params.get("birthday")).getTime())
        );
    }

    @KafkaListener(id = "deleteCat", topics = {"cat.delete"}, containerFactory = "singleFactory")
    public void delete(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        deleteCatById(Integer.parseInt(params.get("id")));
    }

    @KafkaListener(id = "getFriends", topics = {"cat.getFriends"}, containerFactory = "singleFactory")
    public void getFriends(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        makeFriends(
                Integer.parseInt(params.get("firstCat")),
                Integer.parseInt(params.get("secondCat"))
        );
    }

    @KafkaListener(id = "makeFriends", topics = {"cat.makeFriends"}, containerFactory = "singleFactory")
    public void makeFriends(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        makeFriends(
                Integer.parseInt(params.get("firstCat")),
                Integer.parseInt(params.get("secondCat"))
        );
    }


}
