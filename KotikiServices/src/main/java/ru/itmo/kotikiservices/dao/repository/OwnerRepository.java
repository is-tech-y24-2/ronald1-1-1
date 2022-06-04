package ru.itmo.kotikiservices.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotikiservices.dao.model.OwnersEntity;
import ru.itmo.kotikiservices.dao.model.UserEntity;


@Repository
public interface OwnerRepository extends JpaRepository<OwnersEntity, Integer> {
    boolean existsByUser(UserEntity user);
    OwnersEntity findByUser(UserEntity user);
}
