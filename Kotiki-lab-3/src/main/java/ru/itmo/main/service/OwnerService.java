package ru.itmo.main.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.main.dao.model.CatsEntity;
import ru.itmo.main.dao.model.OwnersEntity;
import ru.itmo.main.dao.model.Role;
import ru.itmo.main.dao.model.UserEntity;
import ru.itmo.main.dao.repository.OwnerRepository;
import ru.itmo.main.dao.repository.UserRepository;

import java.sql.Date;
import java.util.ArrayList;
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

    private boolean isAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(s -> s.equals(Role.ADMIN.toString()));
    }

    private UserEntity getUser(){
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }


    public OwnersEntity saveOwner(String name, Date birthday) throws Exception {
        UserEntity user = getUser();
        if(ownerRepository.existsByUser(user)) throw new Exception("Your owner already exist!");
        OwnersEntity ownersEntity = new OwnersEntity();
        ownersEntity.setName(name);
        ownersEntity.setBirthday(birthday);
        ownersEntity.setUser(user);
        ownerRepository.save(ownersEntity);
        return ownersEntity;
    }

    public List<OwnersEntity> getAllOwners(){
        if(isAdmin()) {
            return ownerRepository.findAll();
        }else {
            return Stream.of(ownerRepository.findByUser(getUser())).collect(Collectors.toList());
        }
    }

    public OwnersEntity getOwnerById(int id){
        if(isAdmin()) {
            return ownerRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public void deleteOwner(OwnersEntity ownersEntity) throws Exception {
        if(ownersEntity.getUser().equals(getUser()) || isAdmin())
        ownerRepository.delete(ownersEntity);
        else throw new Exception("It's not your owner");
    }

    public void updateOwner(OwnersEntity ownersEntity) throws Exception {
        if(ownersEntity.getUser().equals(getUser()) || isAdmin())
        ownerRepository.saveAndFlush(ownersEntity);
        else throw new Exception("It's not your owner");
    }

    public Set<CatsEntity> getOwnersCats(int id) throws Exception {
        OwnersEntity ownersEntity = getOwnerById(id);
        if(ownersEntity.getUser().equals(getUser()) || isAdmin())
        return ownersEntity.getCats();
        else throw new Exception("It's not your owner");
    }

    public void deleteOwnerById(int id) {
        ownerRepository.deleteById(id);
    }
}
