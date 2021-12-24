package project1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MyDateTest {
    @Test
    void dateTest(){
        MyDate date1 = new MyDate("2021-8-15");
        assertEquals(date1.getYear(),2021);
        assertEquals(date1.getMonth(),8);
        assertFalse(date1.checkExpiration());
        assertTrue(date1.checkStart());
        //This time must pass
    }
    @Test
    void anotherExpiredTest(){
        MyDate date2 = new MyDate("2022-8-16");
        assertTrue(date2.checkExpiration());
    }
    
}
