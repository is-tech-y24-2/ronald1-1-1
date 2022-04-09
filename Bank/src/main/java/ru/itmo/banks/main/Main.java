package ru.itmo.banks.main;

import ru.itmo.banks.bank.CentralBank;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String line = " ";
        CentralBank centralBank = new CentralBank();
        ConsoleInterface consoleInterface = new ConsoleInterface(centralBank);
        Scanner scanner = new Scanner(System.in);
        while (!line.equals("quit")){
            line = scanner.nextLine();
            consoleInterface.newLine(line);
        }
    }
}
