package ru.itmo.kotiki.dao.crud;

import ru.itmo.kotiki.dao.model.FriendsOfCatsEntity;

public class FriendsOfCatDao extends Dao<FriendsOfCatsEntity> {
    public FriendsOfCatDao() {
        super(FriendsOfCatsEntity.class);
    }
}
