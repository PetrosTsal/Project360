package backend;

import java.util.Date;

public class Customer extends User{
    // attributes
    private java.sql.Date expiration_date;
    private float balance;
    private int credit_limit;
    // ...
    public Customer(){

    }

    public Customer(String username, String password, String name, int account_no, float debt, java.sql.Date expiration_date, float balance, int credit_limit) {
        super(username, password, name, account_no, debt);
        this.expiration_date = expiration_date;
        this.balance = balance;
        this.credit_limit = credit_limit;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(java.sql.Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(int credit_limit) {
        this.credit_limit = credit_limit;
    }

    // functions
}
