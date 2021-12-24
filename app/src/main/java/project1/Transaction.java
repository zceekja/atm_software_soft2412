package project1;

public class Transaction {

    protected int number;
    private TransactionType type;
    private double amount;
    private Double balance;

    public Transaction(TransactionType type, double amount, Double balance, int number) {
        this.number = number;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public void transactionCompleted() {
        this.number ++;
    } //this method should be called after a successful TA to increase TA number

    public void printReceipt() {
        System.out.printf("Receipt\n" +
                "Transaction number: %d\n" +
                "%s amount: %f\n" +
                "Account balance: %f\n", this.number, getTypeToString(this.type), this.amount, this.balance);
    }
    public String getTypeToString(TransactionType t){
        if (t == TransactionType.DEPOSIT){
            return "Deposit";
        }
        if (t == TransactionType.WITHDRAW){
            return "Withdraw";
        }
        return "SOMETHING WRONG";
        
    }
    
}
