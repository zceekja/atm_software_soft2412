package project1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static project1.Atm.CREDENTIALS_STRING;

public class WithdrawTest {
    private Atm atm;
    private Card card;
    private Customer customer;
    private Withdraw withdraw;

    @BeforeEach
    public void setup(){
        atm = new Atm(0);
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        atm.setUp();
        // reset cash in ATM
        try {
            //This for my local database
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement preStatement = connection.prepareStatement("UPDATE Atm SET note100d = ?, note50d =?, note20d =?, note10d =?, note5d =?, coin200c =?, coin100c =?, coin50c =?, coin20c =?, coin10c =?, coin5c =? where id=? ");
            preStatement.setInt(1,50);
            preStatement.setInt(2,50);
            preStatement.setInt(3,50);
            preStatement.setInt(4,50);
            preStatement.setInt(5,50);
            preStatement.setInt(6,50);
            preStatement.setInt(7,50);
            preStatement.setInt(8,50);
            preStatement.setInt(9,50);
            preStatement.setInt(10,50);
            preStatement.setInt(11,50);
            preStatement.setInt(12,atm.getId());
            preStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }


        card = new Card("Peter", "Parker", "5482654047370148", 123456, "22-09-2021", "22-09-2026", false, 100.0);
        customer = new Customer(card, atm);
        withdraw = new Withdraw(atm, customer, 20.0);





//         edit sample user in database to match above info.
        try{
            Connection connection = DriverManager.getConnection(App.CREDENTIALS_STRING);
            PreparedStatement updating = connection.prepareStatement("UPDATE Card SET balance = ? where cardNumber = ?");
            updating.setDouble(1, 100.0); // ensuring the balance is the same.
            updating.setString(2, "5482654047370148ZZ");
            updating.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        this.atm.fetchCash();

    }

    @Test
    public void testConstructor() {
        assertNotNull(withdraw);
    }


    @Test
    public void testWithdrawal() { // ensures customer balance is deducted
        double balance = customer.balance();
        assertEquals(balance, 100.0);
        atm.fetchCash();
        withdraw.withdraw();
        assertEquals(customer.balance(), (balance - 20));
    }

    @Test
    public void testingNotesRemoval() {
        // 1
        double balance = customer.balance();
        int atm20 = atm.getCash().get20note();
        withdraw.withdraw();
        assertEquals(atm.getCash().get20note(), atm20 - 1); // a note should have been removed from the ATM
        assertEquals(customer.balance(), balance - 20.0);
        // 2
        balance = customer.balance();
        try {
            //This for my local database
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement preStatement = connection.prepareStatement("UPDATE Atm SET note100d = ?, note50d =?, note20d =?, note10d =?, note5d =?, coin200c =?, coin100c =?, coin50c =?, coin20c =?, coin10c =?, coin5c =? where id=? ");
            preStatement.setInt(1,50);
            preStatement.setInt(2,50);
            preStatement.setInt(3,0);
            preStatement.setInt(4,1);
            preStatement.setInt(5,2);
            preStatement.setInt(6,50);
            preStatement.setInt(7,50);
            preStatement.setInt(8,50);
            preStatement.setInt(9,50);
            preStatement.setInt(10,50);
            preStatement.setInt(11,50);
            preStatement.setInt(12,atm.getId());
            preStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
        atm.fetchCash();
        withdraw.withdraw();
        assertEquals(atm.getCash().get20note(), 0);
        assertEquals(atm.getCash().get10note(), 0);
        assertEquals(atm.getCash().get5note(), 0);
        assertEquals(customer.balance(), balance - 20);
    }

    @Test
    public void notEnoughMoneyInATM() {
        double balance = customer.balance();
        try {
            //This for my local database
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement preStatement = connection.prepareStatement("UPDATE Atm SET note100d = ?, note50d =?, note20d =?, note10d =?, note5d =?, coin200c =?, coin100c =?, coin50c =?, coin20c =?, coin10c =?, coin5c =? where id=? ");
            preStatement.setInt(1,0);
            preStatement.setInt(2,0);
            preStatement.setInt(3,0);
            preStatement.setInt(4,0);
            preStatement.setInt(5,0);
            preStatement.setInt(6,0);
            preStatement.setInt(7,0);
            preStatement.setInt(8,0);
            preStatement.setInt(9,0);
            preStatement.setInt(10,2);
            preStatement.setInt(11,2);
            preStatement.setInt(12,atm.getId());
            preStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
        atm.fetchCash();

        withdraw.withdraw();


        assertEquals(customer.balance(), balance); // balance should be unchanged since there wasn't enough money in the ATM
        assertEquals(atm.getCash().get5coin(), 2);
        assertEquals(atm.getCash().get10coin(), 2);

        assertEquals(atm.getCash().get100note(), 0);
        assertEquals(atm.getCash().get50note(), 0);
        assertEquals(atm.getCash().get20note(), 0);
        assertEquals(atm.getCash().get10note(), 0);
        assertEquals(atm.getCash().get5note(), 0);
        assertEquals(atm.getCash().get200coin(), 0);
        assertEquals(atm.getCash().get100coin(), 0);
        assertEquals(atm.getCash().get50coin(), 0);
        assertEquals(atm.getCash().get20coin(), 0);


    }

    @Test
    public void invalidNumber() {
        double balance = customer.balance();
        try {
            //This for my local database
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement preStatement = connection.prepareStatement("UPDATE Atm SET note100d = ?, note50d =?, note20d =?, note10d =?, note5d =?, coin200c =?, coin100c =?, coin50c =?, coin20c =?, coin10c =?, coin5c =? where id=? ");
            preStatement.setInt(1,0);
            preStatement.setInt(2,0);
            preStatement.setInt(3,0);
            preStatement.setInt(4,0);
            preStatement.setInt(5,0);
            preStatement.setInt(6,0);
            preStatement.setInt(7,0);
            preStatement.setInt(8,0);
            preStatement.setInt(9,0);
            preStatement.setInt(10,2);
            preStatement.setInt(11,2);
            preStatement.setInt(12,atm.getId());
            preStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
        atm.fetchCash();

        withdraw.withdraw();


        assertEquals(customer.balance(), balance); // balance should be unchanged since there wasn't enough money in the ATM
        assertEquals(atm.getCash().get5coin(), 2);
        assertEquals(atm.getCash().get10coin(), 2);

        assertEquals(atm.getCash().get100note(), 0);
        assertEquals(atm.getCash().get50note(), 0);
        assertEquals(atm.getCash().get20note(), 0);
        assertEquals(atm.getCash().get10note(), 0);
        assertEquals(atm.getCash().get5note(), 0);
        assertEquals(atm.getCash().get200coin(), 0);
        assertEquals(atm.getCash().get100coin(), 0);
        assertEquals(atm.getCash().get50coin(), 0);
        assertEquals(atm.getCash().get20coin(), 0);


    }

    @Test
    public void fetchATM() {
        assertNull(withdraw.fetchAtm()); // checks the 'fetchAtm' function works correctly
    }

    @Test
    public void transaction() {
        Transaction transaction = new Transaction(TransactionType.WITHDRAW, 20.0, customer.balance(), 2);
        withdraw.setTransaction(transaction);
        assertNotNull(withdraw.getTransaction());
    }

    @Test
    public void withdrawEveryCoin() {
        card = new Card("Peter", "Parker", "5482654047370148", 123456, "22-09-2021", "22-09-2026", false, 200.0);
        customer = new Customer(card, atm);
        double balance = customer.balance();
        withdraw = new Withdraw(this.atm, this.customer, 188.85); // this should require one of every note and coin to be deposited
        withdraw.withdraw();

        assertEquals(customer.balance(), balance - 188.85);

        assertEquals(atm.getCash().get100note(), 49);
        assertEquals(atm.getCash().get50note(), 49);
        assertEquals(atm.getCash().get20note(), 49);
        assertEquals(atm.getCash().get10note(), 49);
        assertEquals(atm.getCash().get5note(), 49);
        assertEquals(atm.getCash().get200coin(), 49);
        assertEquals(atm.getCash().get100coin(), 49);
        assertEquals(atm.getCash().get50coin(), 49);
        assertEquals(atm.getCash().get20coin(), 49);
        assertEquals(atm.getCash().get5coin(), 49);
        assertEquals(atm.getCash().get10coin(), 49);


    }




}
