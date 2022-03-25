package ru.itmo.banks.account;

import ru.itmo.banks.bank.Bank;
import ru.itmo.banks.bank.CentralBank;

public class DepositAccount extends Account {
    private final float yearPercent = 365 * 100;
    private int timeLimit;
    private int time;
    private float moneyBuffer;

    public DepositAccount(int clientId, CentralBank centralBank, Bank bank, int timeLimit) {
        super(clientId, centralBank, bank);
        this.timeLimit = timeLimit;
        this.time = 0;
        this.moneyBuffer = nullMoney;
    }

    @Override
    public boolean transaction(IAccount account, float money){
        if (time < timeLimit) {
            return false;
        }

        return super.transaction(account, money);
    }

    @Override
    public boolean withdrawal(float money){
        if (time < timeLimit) {
            return false;
        }

        return super.withdrawal(money);
    }

    @Override
    public void dayPassed() {
        time++;
        if (time <= timeLimit) {
            moneyBuffer += (money + this.bank.getPayPercent()) / yearPercent;
        }

        if (time == timeLimit) {
            money += moneyBuffer;
            moneyBuffer = nullMoney;
        }
    }
}
