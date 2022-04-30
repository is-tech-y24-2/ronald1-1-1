package ru.itmo.main.dao.model;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String text;

    Role(final String text) {
        this.text = text;
    }
}
