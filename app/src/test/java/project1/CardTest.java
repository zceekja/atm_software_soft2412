package project1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
public class CardTest {
    @Test
    void cardTest(){
        Card card1 = new Card("a","b","1234567891234567", 12345, "2021-9-15", "2026-9-15", false, 100, false);
        assertEquals(false, card1.getLostStatus());
        assertEquals("1234567891234567", card1.getCardNumber());
        assertEquals(12345, card1.getPin());
        assertEquals("2021-9-15", card1.getStartDate());
        assertEquals("2026-9-15", card1.getExpirationDate());
        assertEquals(100, card1.getBalance());
        card1.reportedLost();
        assertEquals(true, card1.getLostStatus());
        assertFalse(card1.isExpired());
        assertTrue(card1.isStarted());
        assertEquals("a", card1.getFirstname());
        assertEquals("b", card1.getLastname());
        card1.updateBalance(1000);
        assertEquals(1000, card1.getBalance());
        assertFalse(card1.isBlocked());

        //This time must pass
    }
    @Test
    void cardTestFail(){
        Card card2 = new Card("a","b","1234567891234567", 12345, "2022-9-15", "2026-9-15", false, 100, false);
        assertFalse(card2.isStarted());
        Card card3 = new Card("a","b","1234567891234567", 12345, "1999-9-15", "2000-9-15", false, 100, false);
        assertTrue(card3.isExpired());
        //This time must pass
    }
}
