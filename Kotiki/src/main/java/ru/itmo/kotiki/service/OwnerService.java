package ru.itmo.kotiki.service;

import ru.itmo.kotiki.dao.crud.OwnerDao;
import ru.itmo.kotiki.dao.model.CatsEntity;
import ru.itmo.kotiki.dao.model.OwnersEntity;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public class OwnerService {

    private OwnerDao ownerDao;

    public OwnerService(){
        ownerDao = new OwnerDao();
    }

    public OwnersEntity saveOwner(String name, Date birthday){
        OwnersEntity ownersEntity = new OwnersEntity();
        ownersEntity.setName(name);
        ownersEntity.setBirthday(birthday);
        ownerDao.save(ownersEntity);
        return ownersEntity;
    }

    public List<OwnersEntity> getAllOwners(){
        return ownerDao.findAll();
    }

    public OwnersEntity getOwnerById(int id){
        return ownerDao.findById(id);
    }

    public void deleteOwner(OwnersEntity ownersEntity){
        ownerDao.delete(ownersEntity);
    }

    public void updateOwner(OwnersEntity ownersEntity){
        ownerDao.delete(ownersEntity);
    }

    public Set<CatsEntity> getOwnersCats(int id){
        return getOwnerById(id).getCats();
    }

}
