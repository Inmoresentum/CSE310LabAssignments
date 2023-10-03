package org.cse310.bracu.assignment1;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankAccount {
    private final Integer accountNumber;
    private final String accountName;
    private double balance;
    private final ArrayList<Transaction> listOfTransactions = new ArrayList<>();

    public BankAccount(Integer accountNumber, String accountName) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = 500;
    }


    public static BankAccount createBankAccount(String accountName, Integer accountId) {
        return new BankAccount(accountId, accountName);
    }


    public void deposit(double amount) throws Exception {
        if (amount < 300) {
            throw new Exception("The amount must need to be greater than or equal 300");
        }
        this.balance = this.balance + amount;
        var transaction = new Transaction(LocalDateTime.now(),
                amount,
                TransactionType.DEPOSIT,
                this.balance);
        listOfTransactions.add(transaction);
    }

    public void withdraw(double amount) throws Exception {
        if (amount < 100) {
            throw new Exception("Minimum withdraw amount is 100");
        }
        if (this.balance - amount < 500.0) {
            throw new Exception("Balance Can't go below 500");
        }
        this.balance = this.balance - amount;
        var transaction = new Transaction(LocalDateTime.now(),
                amount,
                TransactionType.DEPOSIT,
                this.balance);
        listOfTransactions.add(transaction);
    }

    public void printStatement() {
        System.out.println(listOfTransactions);
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }
}
