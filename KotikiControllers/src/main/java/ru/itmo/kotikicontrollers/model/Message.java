package ru.itmo.kotikicontrollers.model;

public class Message {

    private String method;
    private Object[] parameters;

    public Message(String method, Object[] parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
