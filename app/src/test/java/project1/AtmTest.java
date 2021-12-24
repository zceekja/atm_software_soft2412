package project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static project1.Atm.CREDENTIALS_STRING;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
public class AtmTest {
    private Atm n;
    private InputStream backup;

    @BeforeEach
    void init(){
        
        n = new Atm(0);
        backup = System.in; // backup System.in to restore it later

    }

//    @Test
//    void maitenanceTest() {
//        Atm atm = new Atm(0);
//        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
//        System.setIn(in);
//        atm.setUp();
//        // reset cash in ATM
//        try {
//            //This for my local database
//            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
//            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
//            PreparedStatement preStatement = connection.prepareStatement("UPDATE Atm SET note100d = ?, note50d =?, note20d =?, note10d =?, note5d =?, coin200c =?, coin100c =?, coin50c =?, coin20c =?, coin10c =?, coin5c =? where id=? ");
//            preStatement.setInt(1,50);
//            preStatement.setInt(2,50);
//            preStatement.setInt(3,50);
//            preStatement.setInt(4,50);
//            preStatement.setInt(5,50);
//            preStatement.setInt(6,50);
//            preStatement.setInt(7,50);
//            preStatement.setInt(8,50);
//            preStatement.setInt(9,50);
//            preStatement.setInt(10,50);
//            preStatement.setInt(11,50);
//            preStatement.setInt(12,atm.getId());
//            preStatement.executeUpdate();
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        atm.fetchCash();
//
//        Card card = new Card("Peter", "Parker", "5482654047370148", 123456, "22-09-2021", "22-09-2026", false, 100.0);
//        Customer customer = new Customer(card, atm);
//
//        InputStream in2 = System.in;
//        System.setIn(new ByteArrayInputStream("1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1".getBytes()));
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(byteArrayOutputStream);
//        PrintStream stdout = System.out;
//        System.setOut(printStream);
//
//        atm.maintenance();
//        System.setIn(in2);
//        System.setOut(stdout);
//
//
//
//        String output2 = byteArrayOutputStream.toString();
//        String key = "output:";
//        String output = output2.substring(output2.indexOf(key) + key.length()).trim();
//        assertTrue(atm.getCash().get100note() > 0);
//        assertTrue(atm.getCash().get50note() > 0);
//        assertTrue(atm.getCash().get20note() > 0);
//        assertTrue(atm.getCash().get10note() > 0);
//        assertTrue(atm.getCash().get5note() > 0);
//        assertTrue(atm.getCash().get100coin() > 0);
//        assertTrue(atm.getCash().get200coin() > 0);
//        assertTrue(atm.getCash().get50coin() > 0);
//        assertTrue(atm.getCash().get20coin() > 0);
//        assertTrue(atm.getCash().get100coin() > 0);
//        assertTrue(atm.getCash().get5coin() > 0);
//        //
//
//    }

  
    @Test
    void setupTest(){
        
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        n.setUp();
        assertEquals(1,n.getId());
        
    }
    @Test
    void getsetCardTest() {
        Card c = new Card("kittibhumi","jaggabatara","1234567891234567",
        123456,"2000-01-01","2500-01-01",false,9999999);
        n.setCard(c);
        assertEquals(c,n.getCard());
    }
    @Test
    void getCashTest(){
        Card c = new Card("kittibhumi","jaggabatara","1234567891234567",
        123456,"2000-01-01","2500-01-01",false,9999999);
        n.setCard(c);
        assertEquals(c,n.getCard());
    }
    @Test
    void cancelTest(){
        n.option = 4;
        assertEquals(true,n.checkCancel());
        n.option = 2;
        assertEquals(false,n.checkCancel());
    }
    @Test
    void transactionlTest(){
        n.transactionCount = 2;
        assertEquals(2,n.getTransaction());
        n.setTransaction();
        assertEquals(3,n.getTransaction());
    }
    @Test
    void proceedTest(){
        n.option = 5;
        n.proceed();
    }
}
