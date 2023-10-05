package org.cse310.bracu.assignment1;

import java.time.LocalDateTime;

public class CheckingAccount extends BankAccount {
    private double overDraftLimit;

    public CheckingAccount(Integer accountNumber, String accountName, double balance, double overDraftLimit) {
        super(accountNumber, accountName);
        super.setBalance(balance);
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
        if (this.getBalance() - amount + overDraftLimit < 500.0) {
            throw new Exception("Balance Can't go below 500");
        }
        this.setBalance(this.getBalance() - amount);
        var transaction = new Transaction(LocalDateTime.now(),
                amount,
                TransactionType.DEPOSIT,
                this.getBalance());
        this.getListOfTransactions().add(transaction);
    }
}
