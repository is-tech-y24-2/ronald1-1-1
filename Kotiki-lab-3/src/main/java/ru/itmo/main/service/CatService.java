package ru.itmo.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.main.dao.model.CatsEntity;
import ru.itmo.main.dao.model.Color;
import ru.itmo.main.dao.model.FriendsOfCatsEntity;
import ru.itmo.main.dao.model.OwnersEntity;
import ru.itmo.main.dao.repository.CatRepository;
import ru.itmo.main.dao.repository.FriendRepository;

import java.sql.Date;
import java.util.List;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private FriendRepository friendRepository;

    public CatsEntity saveCat(String name,
                              String breed,
                              Color color,
                              OwnersEntity owner,
                              Date birthday){
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

    public CatsEntity getCatById(int id){
        return catRepository.findById(id);
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

    public List<CatsEntity> getFriends(int id){
        return getCatById(id).getFriends();
    }

    public void makeFriends(int cat1, int cat2){
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
}
