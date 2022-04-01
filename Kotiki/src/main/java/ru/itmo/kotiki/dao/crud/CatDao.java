package ru.itmo.kotiki.dao.crud;

import ru.itmo.kotiki.dao.model.CatsEntity;

public class CatDao extends Dao<CatsEntity> {

    public CatDao(){
        super(CatsEntity.class);
    }
}
