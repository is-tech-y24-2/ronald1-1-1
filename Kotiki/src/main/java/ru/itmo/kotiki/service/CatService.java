package ru.itmo.kotiki.service;

import ru.itmo.kotiki.dao.crud.CatDao;
import ru.itmo.kotiki.dao.crud.FriendsOfCatDao;
import ru.itmo.kotiki.dao.model.CatsEntity;
import ru.itmo.kotiki.dao.model.Color;
import ru.itmo.kotiki.dao.model.FriendsOfCatsEntity;
import ru.itmo.kotiki.dao.model.OwnersEntity;

import java.sql.Date;
import java.util.List;

public class CatService {

    private CatDao catDao;
    private FriendsOfCatDao friendsOfCatDao;

    public CatService(){
        catDao = new CatDao();
        friendsOfCatDao = new FriendsOfCatDao();
    }

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
        catDao.save(catsEntity);
        return catsEntity;
    }

    public List<CatsEntity> getAllCats(){
        return catDao.findAll();
    }

    public CatsEntity getCatById(int id){
        return catDao.findById(id);
    }

    public void deleteCat(CatsEntity catsEntity){
        catDao.delete(catsEntity);
    }

    public void updateCat(CatsEntity catsEntity){
        catDao.delete(catsEntity);
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
        friendsOfCatDao.save(friendsOfCatsEntity1);
        FriendsOfCatsEntity friendsOfCatsEntity2 = new FriendsOfCatsEntity();
        friendsOfCatsEntity2.setFirstCat(cat2);
        friendsOfCatsEntity2.setSecondCat(cat1);
        friendsOfCatDao.save(friendsOfCatsEntity2);

    }
}
