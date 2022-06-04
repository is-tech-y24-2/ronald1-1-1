package ru.itmo.kotikiservices.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itmo.kotikiservices.dao.model.Color;

import java.sql.Date;

@Data
@AllArgsConstructor
public class CatWrapper {
    private int id;
    private String name;
    private String breed;
    private Color color;
    private Date birthday;
    private int owner;
}
