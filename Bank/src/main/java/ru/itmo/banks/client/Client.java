package ru.itmo.banks.client;

import ru.itmo.banks.account.IAccount;
import ru.itmo.banks.bank.Bank;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private static int id = 0;
    private List<IAccount> accounts;
    private String name;
    private String surname;
    private int clientId;
    private Bank bank;

    public Client(String name, String surname) {
        this.name = name;
        this.clientId = ++id;
        this.surname = surname;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<IAccount> getAccounts() {
        return accounts;
    }

    public void addAccount(IAccount account) {
        accounts.add(account);
    }

    public boolean isConfirmed() {
        return false;
    }

}
