package ru.itmo.banks.bank;

import ru.itmo.banks.account.CreditAccount;
import ru.itmo.banks.account.DebitAccount;
import ru.itmo.banks.account.DepositAccount;
import ru.itmo.banks.account.IAccount;
import ru.itmo.banks.client.Client;
import ru.itmo.banks.Transaction;
import ru.itmo.banks.client.ConfirmedClient;
import ru.itmo.banks.exception.BankException;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    private List<Bank> banks;
    private List<Transaction> transactions;
    private List<Client> clients;
    private List<IAccount> accounts;

    public CentralBank() {
        this.banks = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Bank addBank(String name, float commissionPercent, float payPercent, float creditLimit, float notConfirmedLimit) {
        Bank bank = new Bank(name, commissionPercent, payPercent, creditLimit, notConfirmedLimit);
        banks.add(bank);
        return bank;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void addClientToBank(Client client, Bank bank) {
        bank.addClient(client);
    }

    public DebitAccount addDebitAccount(Client client) {
        DebitAccount debitAccount = new DebitAccount(client.getClientId(), this, client.getBank());
        client.addAccount(debitAccount);
        accounts.add(debitAccount);
        return debitAccount;
    }

    public DepositAccount addDepositAccount(Client client, int timeLimit) {
        DepositAccount depositAccount = new DepositAccount(client.getClientId(), this, client.getBank(), timeLimit);
        client.addAccount(depositAccount);
        accounts.add(depositAccount);
        return depositAccount;
    }

    public CreditAccount addCreditAccount(Client client) {
        CreditAccount creditAccount = new CreditAccount(client.getClientId(), this, client.getBank());
        client.addAccount(creditAccount);
        accounts.add(creditAccount);
        return creditAccount;
    }

    public Client findClient(int id) throws BankException {
        for (Client client : clients) {
            if (client.getClientId() == id) {
                return client;
            }
        }
        throw new BankException("Client not found: " + id);
    }

    public void skipDays(int days){
        for (int i = 0; i < days; i++){
            for(IAccount account : accounts){
                account.dayPassed();
            }
        }
    }

    public Bank findBank(String name) throws BankException {
        for (Bank bank : banks){
            if(bank.getName().equals(name)){
                return bank;
            }
        }

        throw new BankException("Bank not found: " + name);
    }

    public void addClient(Client client){
        clients.add(client);
    }

    public void cancelTransaction(int id) throws BankException {
        Transaction canceledTransaction = null;
        for (Transaction transaction : transactions){
            if(transaction.getTransactionId() == id){
                canceledTransaction = transaction;
            }
        }
        if(canceledTransaction == null){
            throw new BankException("Transaction not found:" + id);
        }

        canceledTransaction.cancel();
        transactions.remove(canceledTransaction);
    }

    public void addPassportToClient(Client client, String passport) throws BankException {
        if(passport.length() != 10){
            throw  new BankException("Invalid passport:" + passport);
        }

        Bank bank = client.getBank();
        clients.remove(client);
        client = new ConfirmedClient(client, passport);
        clients.add(client);
        bank.confirmClient(client);
    }
}
