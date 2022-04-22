package ru.itmo.main.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.main.dao.model.FriendsOfCatsEntity;

@Repository
public interface FriendRepository extends JpaRepository<FriendsOfCatsEntity, Integer> {
}
