package ru.itmo.banks.client;

import ru.itmo.banks.account.IAccount;
import ru.itmo.banks.bank.Bank;

import java.util.ArrayList;
import java.util.List;

public class ConfirmedClient extends ClientDecorator {
    private String passport;

    public ConfirmedClient(Client client, String passport) {
        super(client);
        this.passport = passport;
    }

    @Override
    public Bank getBank() {
        return client.getBank();
    }

    @Override
    public void setBank(Bank bank) {
        client.setBank(bank);
    }

    @Override
    public List<IAccount> getAccounts() {
        return client.getAccounts();
    }

    @Override
    public int getClientId() {
        return client.getClientId();
    }

    @Override
    public void addAccount(IAccount account) {
        client.addAccount(account);
    }

    @Override
    public boolean isConfirmed() {
        return true;
    }
}
