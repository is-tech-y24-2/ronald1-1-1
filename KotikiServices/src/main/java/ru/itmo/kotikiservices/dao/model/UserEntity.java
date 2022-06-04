package ru.itmo.kotikiservices.dao.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "usrs", schema = "public", catalog = "cats")
public class UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Basic
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Basic
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @OneToOne(mappedBy = "user")
    private OwnersEntity userOwner;

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public OwnersEntity getUserOwner(){
        return userOwner;
    }

    public void setUserOwner(OwnersEntity owner){
        this.userOwner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}