package ru.itmo.banks.account;


import ru.itmo.banks.Transaction;
import ru.itmo.banks.bank.Bank;
import ru.itmo.banks.bank.CentralBank;

public class Account implements IAccount {
    private static int id = 0;
    private int accountId;
    protected int clientId;
    protected CentralBank centralBank;
    protected Bank bank;
    protected float money;


    public Account(int clientId, CentralBank centralBank, Bank bank) {
        this.clientId = clientId;
        this.centralBank = centralBank;
        this.bank = bank;
        this.money = 0f;
        this.accountId = ++id;
    }

    public int getId() {
        return accountId;
    }

    @Override
    public float getMoney() {
        return this.money;
    }

    public boolean replenishment(float money) {
        this.money += money;
        return true;
    }

    public boolean withdrawal(float money){
        try {
            if (!centralBank.findClient(clientId).isConfirmed()) {
                if (money > bank.getNotConfirmedLimit()) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.money < money) {
            return false;
        }
        this.money -= money;
        return true;
    }

    public boolean transaction(IAccount account, float money){
        if (withdrawal(money)) {
            account.replenishment(money);
            centralBank.addTransaction(new Transaction(this, account, money));
            return true;
        }
        return false;
    }

    public void changeMoney(float money) {
        this.money += money;
    }

    public void dayPassed() {
    }

}
