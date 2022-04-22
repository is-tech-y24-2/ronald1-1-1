package ru.itmo.main.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.main.dao.model.CatsEntity;
import ru.itmo.main.dao.model.OwnersEntity;
import ru.itmo.main.dao.repository.OwnerRepository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;


    public OwnersEntity saveOwner(String name, Date birthday){
        OwnersEntity ownersEntity = new OwnersEntity();
        ownersEntity.setName(name);
        ownersEntity.setBirthday(birthday);
        ownerRepository.save(ownersEntity);
        return ownersEntity;
    }

    public List<OwnersEntity> getAllOwners(){
        return ownerRepository.findAll();
    }

    public OwnersEntity getOwnerById(int id){
        return ownerRepository.findById(id).get();
    }

    public void deleteOwner(OwnersEntity ownersEntity){
        ownerRepository.delete(ownersEntity);
    }

    public void updateOwner(OwnersEntity ownersEntity){
        ownerRepository.saveAndFlush(ownersEntity);
    }

    public Set<CatsEntity> getOwnersCats(int id){
        return getOwnerById(id).getCats();
    }

    public void deleteOwnerById(int id) {
        ownerRepository.deleteById(id);
    }
}
