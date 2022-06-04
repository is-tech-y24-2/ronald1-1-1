package ru.itmo.kotikiservices.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.kotikiservices.dao.model.CatsEntity;
import ru.itmo.kotikiservices.dao.model.OwnersEntity;
import ru.itmo.kotikiservices.dao.model.UserEntity;
import ru.itmo.kotikiservices.dao.repository.OwnerRepository;
import ru.itmo.kotikiservices.dao.repository.UserRepository;
import ru.itmo.kotikiservices.tool.Util;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;


    public OwnersEntity saveOwner(String name, Date birthday, UserEntity user) throws Exception {
        OwnersEntity ownersEntity = new OwnersEntity();
        ownersEntity.setName(name);
        ownersEntity.setBirthday(birthday);
        ownersEntity.setUser(user);
        ownerRepository.save(ownersEntity);
        return ownersEntity;
    }

    public List<OwnersEntity> getAllOwners(){
        return ownerRepository.findAll();
    }

    public OwnersEntity getOwnerById(int id){
        return ownerRepository.findById(id).get();
    }

    public void deleteOwner(OwnersEntity ownersEntity) throws Exception {
        ownerRepository.delete(ownersEntity);
    }

    public void updateOwner(OwnersEntity ownersEntity) throws Exception {
        ownerRepository.saveAndFlush(ownersEntity);
    }

    public Set<CatsEntity> getOwnersCats(int id) throws Exception {
        OwnersEntity ownersEntity = getOwnerById(id);
        return ownersEntity.getCats();

    }

    public void deleteOwnerById(int id) {
        ownerRepository.deleteById(id);
    }

    @KafkaListener(id = "saveOwner", topics = {"owner.save"}, containerFactory = "singleFactory")
    public void getById(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        saveOwner(
                params.get("name"),
                new Date(new SimpleDateFormat("yyyyMMdd").parse(params.get("birthday")).getTime()),
                userRepository.findByUserName(params.get("user"))
        );
    }

    @KafkaListener(id = "getAllOwners", topics = {"owner.getAll"}, containerFactory = "singleFactory")
    public void getAll(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        getAllOwners().stream().forEach(ownersEntity -> System.out.println(ownersEntity));
    }

    @KafkaListener(id = "getOwner", topics = {"owner.get"}, containerFactory = "singleFactory")
    public void get(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        System.out.println(
                getOwnerById(Integer.parseInt(params.get("id")))
        );
    }

    @KafkaListener(id = "deleteOwner", topics = {"owner.delete"}, containerFactory = "singleFactory")
    public void delete(String object) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params = Util.deserializeFromJSON(object, params.getClass());
        deleteOwnerById(Integer.parseInt(params.get("id")));
    }




}
