package ru.itmo.banks.main;

import ru.itmo.banks.account.IAccount;
import ru.itmo.banks.bank.Bank;
import ru.itmo.banks.bank.CentralBank;
import ru.itmo.banks.client.Client;
import ru.itmo.banks.client.ConfirmedClient;
import ru.itmo.banks.exception.BankException;

import java.awt.color.ICC_ColorSpace;
import java.util.List;

public class ConsoleInterface {
    private CentralBank centralBank;

    public ConsoleInterface(CentralBank centralBank) {
        this.centralBank = centralBank;
    }

    public void newLine(String line) {
        String[] words = line.split(" ");
        String command = words[0];
        if (command.equals("addBank")) {
            addBank(words);
        }
        if (command.equals("skipDays")) {
            skipDays(words);
        }
        if (command.equals("addDebitAccount")) {
            addDebitAccount(words);
        }
        if (command.equals("addDepositAccount")) {
            addDepositAccount(words);
        }
        if (command.equals("addCreditAccount")) {
            addCreditAccount(words);
        }
        if (command.equals("addClient")) {
            addClient(words);
        }
        if (command.equals("cancelTransaction")) {
            cancelTransaction(words);
        }
        if (command.equals("addPassportToClient")) {
            addPassportToClient(words);
        }
        if (command.equals("banks")) {
            banks();
        }
        if (command.equals("bankClients")) {
            bankClients(words);
        }
        if (command.equals("clientAccounts")) {
            clientAccount(words);
        }
    }

    private void clientAccount(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
            return;
        }
        Client client = centralBank.findClient(Integer.getInteger(words[1]));
        for (IAccount account : client.getAccounts()) {
            System.out.println(account.getId());
        }
    }

    private void bankClients(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
            return;
        }
        List<Client> clients = centralBank.findBank(words[1]).getClients();
        for (Client client : clients) {
            System.out.println(client.getName() + " " + client.getSurname() + " " + client.getClientId());
        }
    }

    private void banks() {
        for (Bank bank : centralBank.getBanks()) {
            System.out.println(bank.getName());
        }
    }

    private void addPassportToClient(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
        }
        try {
            centralBank.addPassportToClient(centralBank.findClient(Integer.getInteger(words[1])),
                    words[2]);
        } catch (BankException e) {
            System.out.println("Can't find client");
        }
    }

    private void cancelTransaction(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
            return;
        }

        try {
            centralBank.cancelTransaction(Integer.getInteger(words[1]));
        } catch (BankException e) {
            System.out.println("Invalid line!");
        }
    }

    private void addClient(String[] words) {
        if (words.length > 4 || words.length < 3) {
            System.out.println("Invalid line!");
            return;
        }

        if (words.length == 4) {
            Client client = new Client(words[1], words[2]);
            client = new ConfirmedClient(client, words[3]);
            centralBank.addClient(client);
        } else {
            Client client = new Client(words[1], words[2]);
            centralBank.addClient(client);
        }
    }

    private void addCreditAccount(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
            return;
        }
        try {
            centralBank.addCreditAccount(centralBank.findClient(Integer.getInteger(words[1])));
        } catch (BankException e) {
            System.out.println("Can't find client");
        }
    }

    private void addDepositAccount(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
            return;
        }
        try {
            centralBank.addDepositAccount(centralBank.findClient(Integer.getInteger(words[1])),
                    Integer.getInteger(words[2]));
        } catch (BankException e) {
            System.out.println("Can't find client");
        }
    }

    private void addDebitAccount(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
            return;
        }
        try {
            centralBank.addDebitAccount(centralBank.findClient(Integer.getInteger(words[1])));
        } catch (BankException e) {
            System.out.println("Can't find client");
        }
    }

    private void skipDays(String[] words) {
        if (words.length != 2) {
            System.out.println("Invalid line!");
        }

        centralBank.skipDays(Integer.getInteger(words[1]));
    }

    private void addBank(String[] words) {
        if (words.length != 6) {
            System.out.println("Invalid line!");
            return;
        }
        centralBank.addBank(
                words[1],
                Integer.getInteger(words[2]),
                Integer.getInteger(words[3]),
                Integer.getInteger(words[4]),
                Integer.getInteger(words[5]));

    }
}
