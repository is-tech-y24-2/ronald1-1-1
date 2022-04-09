package ru.itmo.banks.account;

import ru.itmo.banks.bank.Bank;
import ru.itmo.banks.bank.CentralBank;

public class CreditAccount extends Account {
    public CreditAccount(int clientId, CentralBank centralBank, Bank bank) {
        super(clientId, centralBank, bank);
    }

    @Override
    public boolean transaction(IAccount account, float money) {
        if (this.money - money >= -this.bank.getCreditLimit()) {
            return super.transaction(account, money);
        }
        return false;
    }

    @Override
    public boolean withdrawal(float money) {
        if (this.money - money >= -this.bank.getCreditLimit()) {
            try {
                if (!this.centralBank.findClient(clientId).isConfirmed()) {
                    if (money > this.bank.getNotConfirmedLimit()) {
                        return false;
                    }
                }
            } catch (Exception e) {
                return false;
            }
            this.money -= money;
            return true;
        }
        return false;
    }

    @Override
    public void dayPassed() {
        if (this.money < nullMoney && this.money > -this.bank.getCreditLimit()) {
            this.money -= this.bank.getCommission();
        }
    }
}
