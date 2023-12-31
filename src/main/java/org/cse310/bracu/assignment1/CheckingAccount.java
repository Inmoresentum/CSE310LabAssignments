package org.cse310.bracu.assignment1;

import java.time.LocalDateTime;

public class CheckingAccount extends BankAccount {
    private double overDraftLimit;

    public CheckingAccount(Integer accountNumber, String accountName, double overDraftLimit) {
        super(accountNumber, accountName);
        this.overDraftLimit = overDraftLimit;
    }

    public double getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(double overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount < 100) {
            throw new Exception("Minimum withdraw amount is 100");
        }
        if (this.balance - amount + overDraftLimit < 500.0) {
            throw new Exception("Balance Can't go below 500");
        }
        this.balance = this.balance - amount;
        var transaction = new Transaction(LocalDateTime.now(),
                amount,
                TransactionType.DEPOSIT,
                this.balance);
        this.getListOfTransactions().add(transaction);
    }
}
