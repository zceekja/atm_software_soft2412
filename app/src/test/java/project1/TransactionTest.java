package project1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @Test
    void transaction1(){
        Transaction first = new Transaction(TransactionType.DEPOSIT, 100, 100.0, 1);
        assertEquals(first.getTypeToString(TransactionType.DEPOSIT), "Deposit");
        first.transactionCompleted();
        assertEquals(first.number, 2);
        first.printReceipt();
    }
    @Test
    void transaction2(){
        Transaction second = new Transaction(TransactionType.WITHDRAW, 100, 100.0, 1);
        assertEquals(second.getTypeToString(TransactionType.WITHDRAW), "Withdraw");
        assertEquals(second.getTypeToString(null), "SOMETHING WRONG");


    }
}
