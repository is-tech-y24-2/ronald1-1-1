package ru.itmo.kotikiservices.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class OwnerWrapper {
    private int id;
    private String name;
    private Date birthday;
}
