package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Withdraw implements Banking {

    private Transaction transaction;
    private Atm atm;
    private Customer customer;
    private Cash cash;
    private double withdrawAmount;

    public Withdraw(Atm atm, Customer customer, double withdrawAmount) {
        this.atm = atm;
        this.customer = customer;
        atm.fetchCash();
        this.cash = atm.getCash();
        this.withdrawAmount = withdrawAmount;
    }

    @Override
    public Cash fetchAtm() {
        return null;
    }


    @Override
    public boolean withdraw() {

        float variableWithdrawAmt = (float) withdrawAmount;

        // make sure amount of cash in ATM is up to date

        this.cash = atm.getCash();
        atm.fetchCash();

        if(atm.getCash().getTotal() < variableWithdrawAmt) {
            return false;
        }

        while(this.cash.get100note() > 0 && (variableWithdrawAmt - 100 >= 0)) { // check if withdraw amount is multiple of 100 (if $100 is suitable), check if ATM has $100 notes and check if we need to give out more money
            this.cash.remove100note(1); // remove note from ATM
            variableWithdrawAmt -= 100;
        }

        while(this.cash.get50note() > 0 && (variableWithdrawAmt - 50) >= 0) {
            this.cash.remove50note(1); // remove note from ATM
            variableWithdrawAmt -= 50;
        }

        while(this.cash.get20note() > 0 && (variableWithdrawAmt - 20) >= 0) {
            this.cash.remove20note(1); // remove note from ATM
            variableWithdrawAmt -= 20;
        }

        while(this.cash.get10note() > 0 && (variableWithdrawAmt - 10) >= 0) {
            this.cash.remove10note(1); // remove note from ATM
            variableWithdrawAmt -= 10;
        }

        while(this.cash.get5note() > 0 && (variableWithdrawAmt - 5) >= 0) {
            this.cash.remove5note(1); // remove note from ATM
            variableWithdrawAmt -= 5;
        }

        while(this.cash.get200coin() > 0 && (variableWithdrawAmt - 2) >= 0) {
            this.cash.remove200coin(1); // remove note from ATM
            variableWithdrawAmt -= 2;
        }

        while(this.cash.get100coin() > 0 && (variableWithdrawAmt - 1) >= 0) {
            this.cash.remove100coin(1); // remove note from ATM
            variableWithdrawAmt -= 1;
        }

        while(this.cash.get50coin() > 0 && (variableWithdrawAmt - 0.5) >= 0) {
            this.cash.remove50coin(1); // remove note from ATM
            variableWithdrawAmt -= 0.50;
        }

        while(this.cash.get20coin() > 0 && (variableWithdrawAmt - 0.2) >= 0) {
            this.cash.remove20coin(1); // remove note from ATM
            variableWithdrawAmt -= 0.20;
        }

        while(this.cash.get10coin() > 0 && (variableWithdrawAmt - 0.1) >= 0) {
            this.cash.remove10coin(1); // remove note from ATM
            variableWithdrawAmt -= 0.10;
        }

        while(this.cash.get5coin() > 0 && (variableWithdrawAmt - 0.05) >= 0) {
            this.cash.remove5coin(1); // remove note from ATM
            variableWithdrawAmt -= 0.05;
        }


        this.atm.setCash(this.cash);
        double newBalance = this.customer.getCard().getBalance() - withdrawAmount;
        this.customer.getCard().updateBalance(newBalance);
        // update database

        try{
            Connection connection = DriverManager.getConnection(App.CREDENTIALS_STRING);
            PreparedStatement updating = connection.prepareStatement("UPDATE Card SET balance = ? where cardNumber = ?");
            updating.setDouble(1, newBalance);
            updating.setString(2, this.customer.getCard().getCardNumber());
            updating.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        this.atm.updateCash(); // update amount of money in ATM


        return true;




    }

    @Override
    public Transaction getTransaction() {
        return this.transaction;
    }

    @Override
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


}
