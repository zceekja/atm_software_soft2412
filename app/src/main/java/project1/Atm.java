package project1;
import java.util.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;


public class Atm {
    public static final String CREDENTIALS_STRING = "jdbc:mysql://google/bank?cloudSqlInstance=prime-heuristic-320203:australia-southeast1:toby-1234&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=test&password=password";
    private int id;
    private Cash cash;
    private Card card;
    private Customer user;
    protected int transactionCount = 1;
    protected int option;
    public Atm(){
        setUp();
        run();
    }
    public Atm(int test){
        ;
    }
    //run() method that
    void run(){


        while(true) {
            setCustomer();    //set customer
            user.greeting();  //print greeting message to customer
            getOption(); //get user option
            proceed(); 
            if(checkCancel()){ //if user cancel get new customer
                continue;
            };
        }
    }
    
    void setUp(){
//        System.out.println(" --- REMOVE AFTER TESTING --- ");
//        try{
//            Connection connection = DriverManager.getConnection(App.CREDENTIALS_STRING);
//            PreparedStatement updating = connection.prepareStatement("UPDATE Card SET balance = ? where cardNumber = ?");
//            updating.setDouble(1, 0.0);
//            updating.setString(2, "5482654047370148");
//            updating.executeUpdate();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(" ----- BALANCE RESET TO $0 -------");

        Scanner sc = new Scanner(System.in);
        System.out.println("Select ATM number:");
        String id = sc.nextLine();
        try {
            this.id = Integer.parseInt(id);
        } catch(NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            setUp();
        }

        if(this.id < 1 || this.id > 4) {
            System.out.println("Invalid ATM.");
            setUp();
        }
    }

    public void setCash(Cash cash) { // method used for testing
        this.cash = cash;
    }

    void setCustomer(){

        Scanner sc = new Scanner(System.in);
        String card_input;
        int attempt = 3;
        boolean maintenance;
        while(true) {
            maintenance = false;
            do {
                System.out.println("Card number:"); //Ask for card number
                card_input = sc.nextLine();
                if(card_input.equals("admin")){      
                    maintenance = true;
                    break;
                }
            }
            while (!setCard(card_input));  //keep asking for new card until User card match card in database
            //check report lost
            if(maintenance){
                System.out.println("----Perform maintenance---");
                maintenance(); // perform routine maintenance by adding cash in the system
                continue;
            }
            if(getCard().getLostStatus()){
                System.out.println("We ate your card");  //reported lost card
                continue;
            }
            //check startdate
            if(!getCard().isStarted()){
                System.out.println("Card is not activate yet"); //started date is in the future
                continue;
            }
            //check enddate
            if(getCard().isExpired()){
                System.out.println("Card is Expired"); // expired date is in the past
                continue;
            }
            //check isblocked
            if(getCard().isBlocked()){
                System.out.println("You card is blocked"); // card is blocked
                continue;
            }

            attempt =3;  // Set attempt to 3
            while (0 != attempt--) {     //Ask 3 time at most
                System.out.println("Password:");  
                String password = sc.nextLine();
                if(checkPassword(password)){   // if user input match password in database
                    System.out.println("Pass");
                    Customer newCustomer = new Customer(getCard(),this); // set new Customer
                    user = newCustomer;
                    return ;
                }
            }
            getCard().updateSQLBlocked();   //If user put wrong password  3 time, block the card
            System.out.println("Your card is Blocked"); 
        }
    }
    
    /*  in: user input card number
        out: set successful -> true, set fail -> false
    */
    boolean setCard(String cardInput){
        try {
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM Card WHERE cardNumber = ?");
            preStatement.setString(1,cardInput);
            ResultSet resultSet = preStatement.executeQuery();

            if (resultSet.next() == false ){
                //Card not exist in our database
                System.out.println("This card is not valid");
                card = null;
                connection.close();
                return false;
                //dd
            }
            else {
                Card newCard = new Card(
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        cardInput,
                        resultSet.getInt("pin"),
                        resultSet.getDate("startDate").toString(),
                        resultSet.getDate("expirationDate").toString(),
                        resultSet.getBoolean("isStolen"),
                        resultSet.getDouble("balance"),
                        resultSet.getBoolean("is_blocked"));
                card = newCard;
                connection.close();
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    void setCard(Card n){
        card = n;
    }
    Card getCard(){
        return card;
    }
    int getId(){
        return id;
    }

    boolean checkPassword( String input){
        if (getCard().getPin() == Integer.parseInt(input)){
            return true;
        }
        return false;
    }

    void fetchCash(){
        try {
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement preStatement = connection.prepareStatement("SELECT * FROM Atm WHERE id = ?");
            preStatement.setInt(1,getId());
            ResultSet resultSet = preStatement.executeQuery();
            if (resultSet.next() == false ){
                //Card not exist in our database
                System.out.println("Something wrong, Shutting down");
                connection.close();
                System.exit(-1);
            }
            else {
                Cash newCash = new Cash(
                        resultSet.getInt("note100d"),
                        resultSet.getInt("note50d"),
                        resultSet.getInt("note20d"),
                        resultSet.getInt("note10d"),
                        resultSet.getInt("note5d"),
                        resultSet.getInt("coin200c"),
                        resultSet.getInt("coin100c"),
                        resultSet.getInt("coin50c"),
                        resultSet.getInt("coin20c"),
                        resultSet.getInt("coin10c"),
                        resultSet.getInt("coin5c")
                        );
                cash = newCash;
                connection.close();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    void addCash(Cash c){
        cash.add100note(c.get100note());
        cash.add50note(c.get50note());
        cash.add20note(c.get20note());
        cash.add10note(c.get10note());
        cash.add5note(c.get5note());

    }
    void maintenance(){
        fetchCash();
        System.out.println("Total is :"+cash.getTotal());
        System.out.println("---Maintanace---");
        Scanner s = new Scanner(System.in);
        System.out.println("100 note amount: ");
        cash.add100note(s.nextInt());
        System.out.println("50 note amount: ");
        cash.add50note(s.nextInt());
        System.out.println("20 note amount: ");
        cash.add20note(s.nextInt());
        System.out.println("10 note amount: ");
        cash.add10note(s.nextInt());
        System.out.println("5 note amount: ");
        cash.add5note(s.nextInt());
        System.out.println("2 dollar coin amount: ");
        cash.add200coin(s.nextInt());
        System.out.println("1 dollar coin amount: ");
        cash.add100coin(s.nextInt());
        System.out.println("50 cent coin amount: ");
        cash.add50coin(s.nextInt());
        System.out.println("20 cent coin amount: ");
        cash.add20coin(s.nextInt());
        System.out.println("10 cent coin amount: ");
        cash.add10coin(s.nextInt());
        System.out.println("5 cent coin amount: ");
        cash.add5coin(s.nextInt());
        cash.update_total();
        System.out.println("Total is :"+cash.getTotal());
        updateCash();
    }
    void updateCash(){
        try {
            //This for my local database
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            PreparedStatement preStatement = connection.prepareStatement("UPDATE Atm SET note100d = ?, note50d =?, note20d =?, note10d =?, note5d =?, coin200c =?, coin100c =?, coin50c =?, coin20c =?, coin10c =?, coin5c =? where id=? ");
            preStatement.setInt(1,cash.get100note());
            preStatement.setInt(2,cash.get50note());
            preStatement.setInt(3,cash.get20note());
            preStatement.setInt(4,cash.get10note());
            preStatement.setInt(5,cash.get5note());
            preStatement.setInt(6,cash.get200coin());
            preStatement.setInt(7,cash.get100coin());
            preStatement.setInt(8,cash.get50coin());
            preStatement.setInt(9,cash.get20coin());
            preStatement.setInt(10,cash.get10coin());
            preStatement.setInt(11,cash.get5coin());
            preStatement.setInt(12,getId());
            preStatement.executeUpdate();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        //TODO
    }
    void getOption(){
        System.out.println("----Option---");
        System.out.println("[1] Deposit");
        System.out.println("[2] Withdraw");
        System.out.println("[3] Balance");
        System.out.println("[4] Cancel");
        Scanner s = new Scanner(System.in);
        option = s.nextInt();
    }
    void proceed(){
        switch(option){
            case 1:
                System.out.println("---------Deposit---------");
                fetchCash();
                user.deposit();
                break;
            case 2:
                System.out.println("---------Withdraw---------");
                user.withdraw();
                break;
            case 3:
                System.out.println("---------Balance---------");
                user.checkBalance();

                break;
            case 4:
                System.out.println("----Goodbye " +user.getName() + "----");
                break;

            default:

        }
    }
    int getTransaction(){
        return transactionCount;
    }
    boolean checkCancel(){
        if(option ==4){
            option = 0;
            return true;
        }
        return false;
    }
    public void setTransaction(){
        transactionCount++;
    }
    Cash getCash() {
        return this.cash;
    }

}



    /*
        while(true){
            try {
                //This for my local database
                //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
                Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
                Statement statement = connection.createStatement();
                //statement.executeUpdate("insert into card value(4,'Spider')");
                ResultSet resultSet = statement.executeQuery("select * from card");
                while (resultSet.next()) {
                    //if resultSet.getString('card_number') == ""

                    System.out.println(resultSet.getString("firstname"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

         */