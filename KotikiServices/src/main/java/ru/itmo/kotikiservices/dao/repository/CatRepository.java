package ru.itmo.kotikiservices.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotikiservices.dao.model.CatsEntity;
import ru.itmo.kotikiservices.dao.model.Color;
import ru.itmo.kotikiservices.dao.model.OwnersEntity;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<CatsEntity, Integer> {

    CatsEntity findById(int id);
    List<CatsEntity> findAllByColor(Color color);
    List<CatsEntity> findAllByColorAndOwner(Color color, OwnersEntity ownersEntity);



}
