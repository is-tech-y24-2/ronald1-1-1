package ru.itmo.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.main.dao.model.*;
import ru.itmo.main.dao.repository.CatRepository;
import ru.itmo.main.dao.repository.FriendRepository;
import ru.itmo.main.dao.repository.UserRepository;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatService {

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    private boolean isAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(s -> s.equals(Role.ADMIN.toString()));
    }

    private UserEntity getUser(){
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }


    public CatsEntity saveCat(String name,
                              String breed,
                              Color color,
                              OwnersEntity owner,
                              Date birthday) throws Exception {
        if(!owner.equals(getUser().getUserOwner())) throw new Exception("It's not your owner");
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
        if(isAdmin()) {
            return catRepository.findAll();
        }
        return getUser().getUserOwner().getCats().stream().collect(Collectors.toList());
    }

    public List<CatsEntity> getAllCatsByColor(Color color){
        if(isAdmin())
            return catRepository.findAllByColor(color);
        return catRepository.findAllByColorAndOwner(color, getUser().getUserOwner());
    }

    public CatsEntity getCatById(int id) throws Exception {
        CatsEntity catsEntity = catRepository.findById(id);
        if(isAdmin() || catsEntity.getOwner().equals(getUser().getUserOwner()))
            return catsEntity;
        throw new Exception("It's not your cat");
    }

    public void deleteCat(CatsEntity catsEntity){
        if(isAdmin() || catsEntity.getOwner().equals(getUser().getUserOwner()))
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
}
