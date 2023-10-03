package org.cse310.bracu.assignment1;

import java.util.ArrayList;

public class BankAccount {
    private final Integer accountNumber;
    private final String accountName;
    private double balance;
    private final ArrayList<Transaction> listOfTransactions = new ArrayList<>();

    public BankAccount(Integer accountNumber,
                       String accountName,
                       double balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
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
