package ru.itmo.banks.account;

import ru.itmo.banks.bank.Bank;
import ru.itmo.banks.bank.CentralBank;

public class DebitAccount extends Account {
    private float moneyBuffer;
    private int monthDays;

    public DebitAccount(int clientId, CentralBank centralBank, Bank bank) {
        super(clientId, centralBank, bank);
        moneyBuffer = 0;
        monthDays = 0;
    }

    @Override
    public void dayPassed() {
        moneyBuffer += (this.money * this.bank.getPayPercent()) / (365 * 100);
        monthDays++;
        if (monthDays == 30) {
            monthDays = 0;
            super.money += moneyBuffer;
        }
    }
}
