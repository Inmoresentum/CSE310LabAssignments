package org.cse310.bracu.assignment1;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final HashMap<Integer, BankAccount> accountDB = new HashMap<>();
    private static StringTokenizer tk;

    public static void main(String[] args) throws IOException {
        takeInput();
        br.close();
    }

    private static void takeInput() throws IOException {
        String input;
        promptUser();
        while (!(input = new StringTokenizer(br.readLine()).nextToken()).equals("6")) {
            switch (input) {
                case "1" -> handleBankAccountCreation();
                case "2" -> handleDepositAmount();
                case "3" -> handleWithDrawAmount();
                case "4" -> handleCheckBalance();
                case "5" -> handlePrintDetails();
                default -> System.out.println("Please enter a valid number [1-6]");
            }
            promptUser();
        }
        System.out.println("Bye!");
    }

    private static void handleBankAccountCreation() throws IOException {
        System.out.println("1. Normal Account\n2. SavingAccount\n3. CheckingAccount");
        System.out.print("Please enter the type of Bank Account that you want to create: ");
        tk = new StringTokenizer(br.readLine());
        var accountType = tk.nextToken();
        createAccount(accountType);
    }

    private static void createAccount(String accountType) throws IOException {
        switch (accountType) {
            case "1" -> createNormalBankAccount();
            case "2" -> createSavingBankAccount();
            case "3" -> createCheckingBankAccount();
            default -> System.out.println("Please enter a valid input and try again");
        }
    }


    private static void createNormalBankAccount() throws IOException {
        System.out.print("Please enter the AccountName and Account ID [Integer] separated by a space: ");
        tk = new StringTokenizer(br.readLine());
        String accountName = tk.nextToken();
        var accountId = Integer.parseInt(tk.nextToken());
        if (accountDB.containsKey(accountId)) {
            System.out.println("The ID has already been taken! Please try again");
            return;
        }
        var curAccount = new BankAccount(accountId, accountName);
        accountDB.put(accountId, curAccount);
        System.out.println("Done");
    }

    private static void createSavingBankAccount() throws IOException {
        System.out.print("Please enter the AccountName and Account ID [Integer] and Interest Rate separated by a space: ");
        tk = new StringTokenizer(br.readLine());
        String accountName = tk.nextToken();
        var accountId = Integer.parseInt(tk.nextToken());
        var interestRate = Double.parseDouble(tk.nextToken());
        if (accountDB.containsKey(accountId)) {
            System.out.println("The ID has already been taken! Please try again");
            return;
        }
        var curAccount = new SavingAccount(accountId, accountName, interestRate);
        accountDB.put(accountId, curAccount);
        System.out.println("Done");
    }

    private static void createCheckingBankAccount() throws IOException {
        System.out.print("Please enter the AccountName and Account ID [Integer] and Over Draft Limit separated by a space: ");
        tk = new StringTokenizer(br.readLine());
        String accountName = tk.nextToken();
        var accountId = Integer.parseInt(tk.nextToken());
        var overDraftLimit = Double.parseDouble(tk.nextToken());
        if (accountDB.containsKey(accountId)) {
            System.out.println("The ID has already been taken! Please try again");
            return;
        }
        var curAccount = new CheckingAccount(accountId, accountName, overDraftLimit);
        accountDB.put(accountId, curAccount);
        System.out.println("Done");

    }

    private static void handleDepositAmount() throws IOException {
        var accountID = promptAndGetAccountID();
        if (!accountDB.containsKey(accountID)) {
            System.out.println("Please enter a valid ACCOUNT ID and try again");
            return;
        }
        var account = accountDB.get(accountID);

        System.out.print("Please enter the deposit amount: ");
        tk = new StringTokenizer(br.readLine());
        var depositAmount = Double.parseDouble(tk.nextToken());
        try {
            account.deposit(depositAmount);
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println("Please enter a valid deposit amount and try again");
        }
    }

    private static int promptAndGetAccountID() throws IOException {
        System.out.print("Please enter the account ID: ");
        tk = new StringTokenizer(br.readLine());
        return Integer.parseInt(tk.nextToken());
    }

    private static void handleWithDrawAmount() throws IOException {
        var accountID = promptAndGetAccountID();
        if (!accountDB.containsKey(accountID)) {
            System.out.println("Please enter a valid ACCOUNT ID and try again");
            return;
        }
        var account = accountDB.get(accountID);
        System.out.print("Please enter the withdraw amount: ");
        tk = new StringTokenizer(br.readLine());
        var withdrawAmount = Double.parseDouble(tk.nextToken());
        try {
            account.withdraw(withdrawAmount);
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println("Please enter a valid withdraw amount and try again");
        }
    }

    private static void handleCheckBalance() throws IOException {
        var accountID = promptAndGetAccountID();
        if (!accountDB.containsKey(accountID)) {
            System.out.println("Please enter a valid ACCOUNT ID and try again");
            return;
        }
        var account = accountDB.get(accountID);
        System.out.println(account.getBalance());
    }

    private static void handlePrintDetails() throws IOException {
        var accountID = promptAndGetAccountID();
        if (!accountDB.containsKey(accountID)) {
            System.out.println("Please enter a valid ACCOUNT ID and try again");
            return;
        }
        var account = accountDB.get(accountID);
        account.printStatement();
    }

    private static void promptUser() {
        System.out.println("Please choose one of the below options: ");
        System.out.println("1. Create a new bank account.");
        System.out.println("2. Deposit money into an existing account.");
        System.out.println("3. Withdraw money from an existing account.");
        System.out.println("4. Check the balance of an existing account.");
        System.out.println("5. Print a statement of all transactions for a specific account.");
        System.out.println("6. Quit");
    }
}
