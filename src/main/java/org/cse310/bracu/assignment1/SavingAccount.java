package org.cse310.bracu.assignment1;

public class SavingAccount extends BankAccount{
    private double interestRate;

    public SavingAccount(Integer accountNumber, String accountName, double balance, double interestRate) {
        super(accountNumber, accountName);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
