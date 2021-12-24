package project1;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;
import static project1.Atm.CREDENTIALS_STRING;

public class CustomerTest{


    @Test
    void firstCustomer(){
        Card card1 = new Card("a","b","1234567891234567", 12345, "2021-9-15", "2026-9-15", false, 100, false);
        Customer first = new Customer(card1, null);
        assertEquals(first.getCard(), card1);
        assertEquals(first.getName(), "a b");
        first.checkBalance();
        first.greeting();
        assertEquals(first.balance(), 100);
        assertEquals(first.fetchCard(), card1);
    }

    @Test
    public void testDeposit() {
        Atm atm = new Atm(0);
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

        atm.fetchCash();

        Card card = new Card("Peter", "Parker", "5482654047370148", 123456, "22-09-2021", "22-09-2026", false, 100.0);
        Customer customer = new Customer(card, atm);

        InputStream in2 = System.in;
        System.setIn(new ByteArrayInputStream("1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(printStream);

        customer.deposit();
        System.setIn(in2);
        System.setOut(stdout);



        String output2 = byteArrayOutputStream.toString();
        String key = "output:";
        String output = output2.substring(output2.indexOf(key) + key.length()).trim();
        assertEquals(customer.balance(), 285);



    }

    @Test
    public void testWithdraw() {
        Atm atm = new Atm(0);
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

        atm.fetchCash();

        Card card = new Card("Peter", "Parker", "5482654047370148", 123456, "22-09-2021", "22-09-2026", false, 0);
        Customer customer = new Customer(card, atm);



        InputStream in2 = System.in;
        System.setIn(new ByteArrayInputStream("10".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(printStream);

        customer.withdraw();
        System.setIn(in2);
        System.setOut(stdout);



        String output2 = byteArrayOutputStream.toString();
        String key = "output:";
        String output = output2.substring(output2.indexOf(key) + key.length()).trim();
        assertEquals(output, "amount to withdraw. $0.0 available.\n" +
                "Insufficient balance.");
    }

    @Test
    public void testNormalWithdraw() {
        Atm atm = new Atm(0);
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

        atm.fetchCash();

        Card card = new Card("Peter", "Parker", "5482654047370148", 123456, "22-09-2021", "22-09-2026", false, 100.0);
        Customer customer = new Customer(card, atm);



        InputStream in2 = System.in;
        System.setIn(new ByteArrayInputStream("100".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(printStream);

        customer.withdraw();
        System.setIn(in2);
        System.setOut(stdout);



        String output2 = byteArrayOutputStream.toString();
        String key = "output:";
        String output = output2.substring(output2.indexOf(key) + key.length()).trim();

        assertEquals(customer.balance(), 0);

        customer.checkBalance();

        assertNotNull(customer.fetchCard());
    }


}