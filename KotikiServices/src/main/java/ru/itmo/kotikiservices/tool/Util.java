package ru.itmo.kotikiservices.tool;


import com.google.gson.Gson;
import ru.itmo.kotikiservices.dao.model.CatsEntity;
import ru.itmo.kotikiservices.dao.model.OwnersEntity;
import ru.itmo.kotikiservices.wrapper.CatWrapper;
import ru.itmo.kotikiservices.wrapper.OwnerWrapper;

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

    public static String serializeToJSON(Object object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static <T> T deserializeFromJSON(String json, Class<T> type){
        Gson gson = new Gson();
        T object = gson.fromJson(json, type);
        return object;
    }
}
