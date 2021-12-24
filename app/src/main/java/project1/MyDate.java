package project1;
import java.sql.Date;
import java.time.*;
public class MyDate {
    private int year;
    private int month;
    private int day;
    public MyDate(String x){
        parse(x);
        
    }
    public void parse(String fm){
        String [] splitFm = fm.split("-");
        this.year = Integer.parseInt(splitFm[0]);
        this.month = Integer.parseInt(splitFm[1]);
    }
    public int getYear(){
        return this.year;
    }
    public int getMonth(){
        return this.month;
    }
    //return false if expired
    public boolean checkExpiration(){ 
        LocalDate rightNow = LocalDate.now();
        //2019-07-24
        if(this.year < rightNow.getYear()){
            return false;
        }
        else if(this.year == rightNow.getYear() && this.getMonth() < rightNow.getMonthValue()){
            return false;
        }
        return true;

    }
    //return false if startDate of card is after the currrent date
    public boolean checkStart(){
        LocalDate rightNow = LocalDate.now();
        //2019-07-24
        if(this.year > rightNow.getYear()){
            return false;
        }
        else if(this.year == rightNow.getYear() && this.getMonth() > rightNow.getMonthValue()){
            return false;
        }
        return true;

    }
}