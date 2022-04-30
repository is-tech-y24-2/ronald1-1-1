package ru.itmo.main.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.main.dao.model.CatsEntity;
import ru.itmo.main.dao.model.Color;
import ru.itmo.main.dao.model.OwnersEntity;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<CatsEntity, Integer> {

    CatsEntity findById(int id);
    List<CatsEntity> findAllByColor(Color color);
    List<CatsEntity> findAllByColorAndOwner(Color color, OwnersEntity ownersEntity);



}
