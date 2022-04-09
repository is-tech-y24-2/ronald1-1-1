package ru.itmo.kotiki.dao.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "friends", schema = "public", catalog = "cats")
public class FriendsOfCatsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "first")
    private int firstCat;
    @Basic
    @Column(name = "second")
    private int secondCat;

//    @ManyToMany(mappedBy = "friends")
//    private List<CatsEntity> cats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstCat() {
        return firstCat;
    }

    public void setFirstCat(int firstCat) {
        this.firstCat = firstCat;
    }

    public int getSecondCat() {
        return secondCat;
    }

    public void setSecondCat(int secondCat) {
        this.secondCat = secondCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendsOfCatsEntity that = (FriendsOfCatsEntity) o;
        return id == that.id && firstCat == that.firstCat && secondCat == that.secondCat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstCat, secondCat);
    }
}
