package project1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {
    private String firstname;
    private String lastname;
    private Atm atm;
    Cash cashDeposit;
    private Card card;

    public Customer(Card c, Atm a){
        card = c;
        atm = a;

        firstname = card.getFirstname();
        lastname = card.getLastname();
    }

    public Card getCard() {
        return this.card;
    }
    public  void greeting(){
        System.out.println("Hello " + firstname +" "+ lastname);
    }
    public String getName(){
        return firstname + " " + lastname;
    }

    public void deposit(){
        // make sure amount of cash in ATM is up to date
        atm.fetchCash();

        Scanner s = new Scanner(System.in);
        double total = 0.0;
        System.out.println("-------Deposit-------");
        double newBalance;
        try {
            System.out.println("Amount of 100 dollar note: ");
            int note100 = s.nextInt();
            total += note100 * 100.0;
            System.out.println("Amount of 50 dollar note: ");
            int note50 = s.nextInt();
            total += note50 * 50.0;
            System.out.println("Amount of 20 dollar note: ");
            int note20 = s.nextInt();
            total += note20 * 20.0;
            System.out.println("Amount of 10 dollar note: ");
            int note10 = s.nextInt();
            total += note10 * 10.0;
            System.out.println("Amount of 5 dollar note: ");
            int note5 = s.nextInt();
            total += note5 * 5.0;
            // update the user's balance
            newBalance = card.getBalance() + total;
            card.updateBalance(newBalance);

            // update the ATM
            Cash cash = atm.getCash();

            cash.add100note(note100);
            cash.add50note(note50);
            cash.add20note(note20);
            cash.add10note(note10);
            cash.add5note(note5);


            // update database

            try{
                Connection connection = DriverManager.getConnection(App.CREDENTIALS_STRING);
                PreparedStatement updating = connection.prepareStatement("UPDATE Card SET balance = ? where cardNumber = ?"); // not sure if it's called balance ?
                updating.setDouble(1, newBalance);
                updating.setString(2, this.getCard().getCardNumber());
                updating.executeUpdate();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            this.atm.updateCash(); // update amount of money in ATM

            // print receipt
            Transaction transaction = new Transaction(TransactionType.DEPOSIT, total, this.card.getBalance(),atm.getTransaction());
            transaction.printReceipt();
            transaction.transactionCompleted();
            atm.setTransaction();
        } catch(InputMismatchException e) {
            System.out.println("Invalid Input. Try again.");
            deposit();
        }









    }

    public void withdraw(){

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter amount to withdraw. $" + Double.toString(getCard().getBalance()) + " available.");
        String amount = scanner.nextLine();

        double amountDoub = 0.0;
        try{
            amountDoub = Double.parseDouble(amount);
        }
        catch (NumberFormatException ex){
            System.out.println("Please enter a valid number.");
            this.withdraw();
        }

        if(amountDoub > card.getBalance()){
            System.out.println("Insufficient balance.\n");
            return ;
        }
        if(amountDoub <= 0.0) {
            System.out.println("Please enter a positive number.");
            this.withdraw();
        }

        if(Math.floor(amountDoub/0.05) != Math.ceil(amountDoub/0.05)) {
            System.out.println("Please enter a withdrawal amount as a multiple of 5c.");
            this.withdraw();
        }

        Withdraw withdrawInst = new Withdraw(this.atm, this, amountDoub);

        boolean withdraw = withdrawInst.withdraw();

        if(withdraw == false) {
            System.out.println("Sorry. This ATM does not have enough money to complete your transaction.\nNo money has been removed from your account.");
        } else {
            Transaction transaction = new Transaction(TransactionType.WITHDRAW, amountDoub, card.getBalance(),atm.getTransaction());
            transaction.printReceipt();
            transaction.transactionCompleted();
            atm.setTransaction();
        }


    }
    public double balance() {
        return this.card.getBalance();
    }
    public void checkBalance(){
        System.out.println("Your account have " + this.card.getBalance()  + " amount left.");
    }


    public Card fetchCard() {
        return this.card;

    }
}
