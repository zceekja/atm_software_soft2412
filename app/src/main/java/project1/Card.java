package project1;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Card{
    public static final String CREDENTIALS_STRING = "jdbc:mysql://google/bank?cloudSqlInstance=prime-heuristic-320203:australia-southeast1:toby-1234&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=test&password=password";
    private String firstname;
    private String lastname;
    private String cardNumber;
    private int pin;
    private String startDate;
    private String expirationDate;
    private boolean reported_lost;
    private double balance;
    private boolean blocked;
    
    public Card(String firstname, String lastname, String cardNumber, int pin, String startDate, String expirationDate, boolean reported_lost, double balance) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.reported_lost = reported_lost;
        this.balance = balance;
        this.blocked = false;
    }

    public Card(String firstname, String lastname, String cardNumber, int pin, String startDate, String expirationDate, boolean reported_lost, double balance, boolean blocked) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.reported_lost = reported_lost;
        this.balance = balance;
        this.blocked = blocked;
    }
    public void updateBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void reportedLost() {
        this.reported_lost = true;
    }
    public String getCardNumber() {
        return this.cardNumber;
    }
    public String getFirstname(){return firstname;}
    public String getLastname(){return lastname;}

    public int getPin(){
        return this.pin;
    }
    public String getStartDate(){
        return this.startDate;
    }
    public String getExpirationDate(){
        return this.expirationDate;
    }
    public boolean getLostStatus(){
        return this.reported_lost;
    }
    public boolean isBlocked(){
        return this.blocked;
    }
    public boolean isExpired(){
        //TODO
        MyDate cardDate = new MyDate(this.expirationDate);
        // if true --> card expired
        if(cardDate.checkExpiration() == false){
            return true;
        }


        return false;
    }
    public boolean isStarted(){
        MyDate cardDate = new MyDate(this.startDate);
        // if true --> card can be used
        if(cardDate.checkStart() == false){
            return false;
        }
        //TODO

        return true;
    }
    public void updateSQLBlocked(){
        //update false to true
        try{
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement updating = connection.prepareStatement("UPDATE Card SET is_blocked = ? where cardNumber = ?");
            updating.setBoolean(1, true);
            updating.setString(2, this.cardNumber);
            updating.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public double getBalance(){
        return this.balance;
    }

}