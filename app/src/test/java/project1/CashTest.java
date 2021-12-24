package project1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CashTest {
    @Test
    void constructionTest(){
        Cash cash = new Cash(11,10,9,8,7,6,5,4,3,2,1);
        assertEquals(1914.85,cash.getTotal());
        Cash cash2 = new Cash(0,0,0,0,0,0,0,0,0,0,0);
        assertEquals(0,cash2.getTotal());
        //This time must pass
        cash2.add100note(1);
        cash2.add50note(1);
        cash2.add20note(1);
        cash2.add10note(1);
        cash2.add5note(1);
        cash2.add200coin(1);
        cash2.add100coin(1);
        cash2.add50coin(1);
        cash2.add20coin(1);
        cash2.add5coin(1);
        cash2.add10coin(1);
        cash2.remove100note(1);
        cash2.remove50note(1);
        cash2.remove20note(1);
        cash2.remove10note(1);
        cash2.remove5note(1);
        cash2.remove100coin(1);
        cash2.remove200coin(1);
        cash2.remove50coin(1);
        cash2.remove20coin(1);
        cash2.remove10coin(1);
        cash2.remove5coin(1);
        assertEquals(0,cash2.get100note());
        assertEquals(0,cash2.get50note());
        assertEquals(0,cash2.get20note());
        assertEquals(0,cash2.get10note());
        assertEquals(0,cash2.get5note());
        assertEquals(0,cash2.get200coin());
        assertEquals(0,cash2.get100coin());
        assertEquals(0,cash2.get50coin());
        assertEquals(0,cash2.get20coin());
        assertEquals(0,cash2.get10coin());
        assertEquals(0,cash2.get5coin());

    }


}
