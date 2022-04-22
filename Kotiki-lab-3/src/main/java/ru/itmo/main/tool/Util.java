package ru.itmo.main.tool;

import ru.itmo.main.dao.model.CatsEntity;
import ru.itmo.main.dao.model.OwnersEntity;
import ru.itmo.main.wrapper.CatWrapper;
import ru.itmo.main.wrapper.OwnerWrapper;

public class Util {

    public static CatWrapper getWrapperFromEntity(CatsEntity catsEntity){
        return new CatWrapper(catsEntity.getId(),
                catsEntity.getName(),
                catsEntity.getBreed(),
                catsEntity.getColor(),
                catsEntity.getBirthday(),
                catsEntity.getOwner().getId());
    }

    public static OwnerWrapper getWrapperFromEntity(OwnersEntity ownersEntity){
        return new OwnerWrapper(ownersEntity.getId(),
                ownersEntity.getName(),
                ownersEntity.getBirthday());
    }
}
