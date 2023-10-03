package org.cse310.bracu.assignment1;

public class CheckingAccount extends BankAccount {
    private double overDraftLimit;

    public CheckingAccount(Integer accountNumber, String accountName, double balance, double overDraftLimit) {
        super(accountNumber, accountName, balance);
        this.overDraftLimit = overDraftLimit;
    }

    public double getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(double overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }
}
