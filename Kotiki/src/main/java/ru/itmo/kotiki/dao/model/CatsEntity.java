package ru.itmo.kotiki.dao.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cats", schema = "public", catalog = "cats")
public class CatsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "breed")
    private String breed;
    @Basic
    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private OwnersEntity owner;

    @Basic
    @Column(name = "birthday")
    private Date birthday;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "friends",
                joinColumns = @JoinColumn(name = "first"),
                inverseJoinColumns = @JoinColumn(name = "second"))
    private List<CatsEntity> friends;

    public List<CatsEntity> getFriends() {
        return friends;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public OwnersEntity getOwner() {
        return owner;
    }

    public void setOwner(OwnersEntity owner) {
        this.owner = owner;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatsEntity that = (CatsEntity) o;
        return id == that.id && owner == that.owner && Objects.equals(name, that.name) && Objects.equals(breed, that.breed) && Objects.equals(color, that.color) && Objects.equals(birthday, that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, breed, color, owner, birthday);
    }
}
