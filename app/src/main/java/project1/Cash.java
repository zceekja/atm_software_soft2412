package project1;

public class Cash {
    private int note100;
    private int note50;
    private int note20;
    private int note10;
    private int note5;
    private int coin200;
    private int coin100;
    private int coin50;
    private int coin20;
    private int coin10;
    private int coin5;
    private double total_cash;

    public Cash(int n100, int n50, int n20, int n10, int n5,
                int c200, int c100, int c50, int c20, int c10, int c5) {
        note100 = n100;
        note50 = n50;
        note20 = n20;
        note10 = n10;
        note5 = n5;
        coin200 = c200;
        coin100 = c100;
        coin50 = c50;
        coin20 = c20;
        coin10 = c10;
        coin5 = c5;
        update_total();
    }

    public void update_total() {
        total_cash = note100 * 100 + note50 * 50 + note20 * 20 + note10 * 10 + note5 * 5 + coin200 * 2 + coin100 * 1 +
                coin50 * 0.5 + coin20 * 0.2 + coin10 * 0.1 + coin5 * 0.05;
        return;
    }
    public void add100note(int x){
        note100 +=x;
    }
    public void add50note(int x){
        note50 +=x;
    }
    public void add20note(int x){
        note20 +=x;
    }
    public void add10note(int x){
        note10 +=x;
    }
    public void add5note(int x){
        note5 +=x;
    }
    public void add200coin(int x){
        coin200 +=x;
    }
    public void add100coin(int x){
        coin100 +=x;
    }
    public void add50coin(int x){
        coin50 +=x;
    }
    public void add20coin(int x){
        coin20 +=x;
    }
    public void add10coin(int x){
        coin10 +=x;
    }
    public void add5coin(int x){
        coin5 +=x;
    }

    public void remove100note(int x){
        note100 -=x;
    }
    public void remove50note(int x){
        note50 -=x;
    }
    public void remove20note(int x){
        note20 -=x;
    }
    public void remove10note(int x){
        note10 -=x;
    }
    public void remove5note(int x){
        note5 -=x;
    }
    public void remove200coin(int x){
        coin200 -=x;
    }
    public void remove100coin(int x){
        coin100 -=x;
    }
    public void remove50coin(int x){
        coin50 -=x;
    }
    public void remove20coin(int x){
        coin20 -=x;
    }
    public void remove10coin(int x){
        coin10 -=x;
    }
    public void remove5coin(int x){
        coin5 -=x;
    }

    public int get100note(){
        return note100;
    }
    public int get50note(){
        return note50;
    }
    public int get20note(){
        return note20;
    }
    public int get10note(){
        return note10;
    }
    public int get5note(){
        return note5;
    }
    public int get200coin(){
        return coin200;
    }
    public int get100coin(){
        return coin100;
    }
    public int get50coin(){
        return coin50;
    }
    public int get20coin(){
        return coin20;
    }
    public int get10coin(){
        return coin10;
    }
    public int get5coin(){
        return coin5;
    }

    public double getTotal() {
        return total_cash;
    }
}

