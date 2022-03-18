package ru.itmo.banks;

import ru.itmo.banks.account.IAccount;

public class Transaction {
    private static int id = 0;



    private IAccount accountFrom;
    private IAccount accountTo;
    private  float money;
    private int transactionId;

    public Transaction(IAccount accountFrom, IAccount accountTo, float money){
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
        this.transactionId = ++id;
    }

    public IAccount getAccountFrom() {
        return accountFrom;
    }

    public IAccount getAccountTo() {
        return accountTo;
    }

    public float getMoney() {
        return money;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void cancel(){
        accountFrom.changeMoney(money);
        accountTo.changeMoney(-money);
    }

}
