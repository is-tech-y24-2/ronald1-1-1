package ru.itmo.kotiki.dao.model;

import javax.persistence.Enumerated;


public enum Color {
    RED("Red"),
    BLACK("Black"),
    GRAY("Gray"),
    WHITE("White");

    private final String text;

    Color(final String text) {
        this.text = text;
    }
}
