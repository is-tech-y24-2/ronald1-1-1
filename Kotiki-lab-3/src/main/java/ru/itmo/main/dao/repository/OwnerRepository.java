package ru.itmo.main.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.main.dao.model.OwnersEntity;
import ru.itmo.main.dao.model.UserEntity;

@Repository
public interface OwnerRepository extends JpaRepository<OwnersEntity, Integer> {
    boolean existsByUser(UserEntity user);
    OwnersEntity findByUser(UserEntity user);
}
