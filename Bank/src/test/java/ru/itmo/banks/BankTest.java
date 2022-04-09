package ru.itmo.banks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.itmo.banks.account.CreditAccount;
import ru.itmo.banks.account.DebitAccount;
import ru.itmo.banks.account.DepositAccount;
import ru.itmo.banks.account.IAccount;
import ru.itmo.banks.bank.Bank;
import ru.itmo.banks.bank.CentralBank;
import ru.itmo.banks.client.Client;
import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    private static CentralBank centralBank;

    @BeforeAll
    public static void setUp(){
        centralBank = new CentralBank();
    }

    @Test
    public void DebitAccount(){
        Bank bank = centralBank.addBank("1", 3.65f, 3.65f, 1000f, 2000);
        Client client1 = new Client("Maria", "Bubkina");
        centralBank.addClient(client1);
        centralBank.addClientToBank(client1, bank);
        DebitAccount account1 = centralBank.addDebitAccount(client1);
        Client client2 = new Client("Kolya", "Shishkin");
        centralBank.addClient(client2);
        centralBank.addClientToBank(client2, bank);
        DebitAccount account2 = centralBank.addDebitAccount(client1);
        account2.replenishment(1000);
        account1.replenishment(1000);
        centralBank.skipDays(30);
        assertTrue(Math.abs(account1.getMoney() - 1003) < 0.01);
        try {
            account1.withdrawal(103f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(Math.abs(account1.getMoney() - 900) < 0.01);
        try {
            account2.transaction(account1, 103);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(Math.abs(account1.getMoney() - 1003) < 0.01);
        assertTrue(Math.abs(account2.getMoney() - 900) < 0.01);
    }

    @Test
    public void DepositAccount()
    {
        Bank bank = centralBank.addBank("1", 3.65f, 3.65f, 1000f, 2000);
        Client client1 = new Client("Maria", "Bubkina");
        centralBank.addClient(client1);
        centralBank.addClientToBank(client1, bank);
        DepositAccount account1 = centralBank.addDepositAccount(client1, 30);
        Client client2 = new Client("Kolya", "Shishkin");
        centralBank.addClient(client2);
        centralBank.addClientToBank(client2, bank);
        DepositAccount account2 = centralBank.addDepositAccount(client1, 40);
        account2.replenishment(1000);
        account1.replenishment(1000);
        centralBank.skipDays(35);
        assertTrue(Math.abs(account1.getMoney() - 1000.82f) < 0.01);
        account1.withdrawal(100.82f);

        assertTrue(Math.abs(account1.getMoney() - 900) < 0.01);
        assertFalse(account2.transaction(account1, 103));
        assertFalse(account2.withdrawal( 103));
        centralBank.skipDays(5);
        assertTrue(Math.abs(account2.getMoney() - 1001.09) < 0.01);
    }

    @Test
    public void CreditAccount()
    {
        Bank bank = centralBank.addBank("1", 1f, 3.65f, 2000f, 2000);
        var client1 = new Client("Maria", "Bubkina");
        centralBank.addClient(client1);
        centralBank.addClientToBank(client1, bank);
        CreditAccount account1 = centralBank.addCreditAccount(client1);
        account1.replenishment(1000);
        centralBank.skipDays(30);
        assertTrue(Math.abs(account1.getMoney() - 1000) < 0.01);
        account1.withdrawal(2000);
        centralBank.skipDays(30);
        assertTrue(Math.abs(account1.getMoney() - (-1030)) < 0.01);
        assertFalse(account1.withdrawal(2000f));
    }

    @Test
    public void ConfirmClient()
    {
        Bank bank = centralBank.addBank("1", 1f, 3.65f, 2000f, 2000);
        var client1 = new Client("Maria", "Bubkina");
        centralBank.addClient(client1);
        centralBank.addClientToBank(client1, bank);
        IAccount account1 = centralBank.addDebitAccount(client1);
        account1.replenishment(3000);
        assertFalse(account1.withdrawal(2500));
        try {
            centralBank.addPassportToClient(client1, "1111111111");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            client1 = centralBank.findClient(client1.getClientId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        account1 = client1.getAccounts().get(0);
        assertTrue(account1.withdrawal(2500));
    }

    public void Transactions()
    {
        Bank bank = centralBank.addBank("1", 3.65f, 3.65f, 1000f, 2000);
        var client1 = new Client("Maria", "Bubkina");
        centralBank.addClient(client1);
        centralBank.addClientToBank(client1, bank);
        DebitAccount account1 = centralBank.addDebitAccount(client1);
        var client2 = new Client("Kolya", "Shishkin");
        centralBank.addClient(client2);
        centralBank.addClientToBank(client2, bank);
        DebitAccount account2 = centralBank.addDebitAccount(client2);
        account2.replenishment(1000);
        account1.replenishment(1000);
        account2.transaction(account1, 500);
        assertTrue(Math.abs(account1.getMoney() - 1500) < 0.01);
        assertTrue(Math.abs(account2.getMoney() - 500) < 0.01);
        try {
            centralBank.cancelTransaction(centralBank.getTransactions().get(0).getTransactionId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(Math.abs(account1.getMoney() - 1000) < 0.01);
        assertTrue(Math.abs(account2.getMoney() - 1000) < 0.01);
    }
}
