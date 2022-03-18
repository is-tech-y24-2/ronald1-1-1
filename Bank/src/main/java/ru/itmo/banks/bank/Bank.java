package ru.itmo.banks.bank;

import ru.itmo.banks.client.Client;
import ru.itmo.banks.account.IAccount;

import java.util.ArrayList;

public class Bank {
    private String name;
    private ArrayList<Client> clients;
    private float commission;
    private float payPercent;
    private float creditLimit;
    private float notConfirmedLimit;

    public Bank(String name, float commission, float payPercent, float creditLimit, float notConfirmedLimit){
        this.clients = new ArrayList<>();
        this.name = name;
        this.commission = commission;
        this.payPercent = payPercent;
        this.creditLimit = creditLimit;
        this.notConfirmedLimit = notConfirmedLimit;
    }

    public ArrayList<Client> getClients(){
        return (ArrayList<Client>) clients.clone();
    }

    public float getCommission() {
        return commission;
    }

    public String getName() {
        return name;
    }

    public float getPayPercent() {
        return payPercent;
    }

    public float getCreditLimit() {
        return creditLimit;
    }

    public float getNotConfirmedLimit() {
        return notConfirmedLimit;
    }

    public void addAccountToClient(Client client, IAccount account){
        client.addAccount(account);
    }

    public void addClient(Client client){
        client.setBank(this);
        clients.add(client);
    }

    public void confirmClient(Client confirmedClient){
        Client oldClient = null;
        for(Client client : clients){
            if (client.getClientId() == confirmedClient.getClientId()){
                oldClient = client;
            }
        }
        clients.remove(oldClient);
        clients.add(confirmedClient);
    }



}
