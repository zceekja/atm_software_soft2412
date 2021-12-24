package project1;

public interface Banking {

    public Cash fetchAtm();

    public boolean withdraw();

    public Transaction getTransaction();

    public void setTransaction(Transaction transaction);
}
