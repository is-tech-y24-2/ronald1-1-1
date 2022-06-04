package ru.itmo.kotikiservices.dao.model;

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
