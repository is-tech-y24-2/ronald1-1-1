package ru.itmo.banks.account;

public interface IAccount {

    int getId();
    float getMoney();
    boolean replenishment(float money);
    boolean withdrawal(float money);
    boolean transaction(IAccount account, float money);

    void dayPassed();
    void changeMoney(float money);
}
