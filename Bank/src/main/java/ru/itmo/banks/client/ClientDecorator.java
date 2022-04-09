package ru.itmo.banks.client;

public class ClientDecorator extends Client{
    protected Client client;


    public ClientDecorator(Client client) {
        super(client.getName(), client.getSurname());
        this.client = client;
    }
}
