package org.cse310.bracu.assignment1;

import java.time.LocalDateTime;

public class Transaction {
    private final LocalDateTime localDateTime;
    private final double amount;
    private final TransactionType transactionType;
    private final double balanceAfterTransaction;

    public Transaction(LocalDateTime localDateTime,
                       double amount,
                       TransactionType transactionType,
                       double balanceAfterTransaction) {
        this.localDateTime = localDateTime;
        this.amount = amount;
        this.transactionType = transactionType;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public double getAmount() {
        return amount;
    }


    public TransactionType getTransactionType() {
        return transactionType;
    }


    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "localDateTime=" + localDateTime +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", balanceAfterTransaction=" + balanceAfterTransaction +
                '}';
    }
}
