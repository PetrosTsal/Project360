package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transaction {
    // attributes
    private int transactionID;
    private String dealerName;
    private int dealerAccount_no;
    private String customerName;
    private int customerAccount_no;
    private java.sql.Date date;
    private double amount;
    private String type;

    public Transaction(){
        this.type = "charge/credit";
    }

    public Transaction(int transactionID, String dealerName, int dealerAccount_no, String customerName, int customerAccount_no, Date date, float amount) {
        this.transactionID = transactionID;
        this.dealerName = dealerName;
        this.dealerAccount_no = dealerAccount_no;
        this.customerName = customerName;
        this.customerAccount_no = customerAccount_no;
        this.date = date;
        this.amount = amount;
        this.type = "charge/credit";
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public int getDealerAccount_no() {
        return dealerAccount_no;
    }

    public void setDealerAccount_no(int dealerAccount_no) {
        this.dealerAccount_no = dealerAccount_no;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerAccount_no() {
        return customerAccount_no;
    }

    public void setCustomerAccount_no(int customerAccount_no) {
        this.customerAccount_no = customerAccount_no;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // functions
    public static Transaction getTransaction(String transactionId) throws ClassNotFoundException, SQLException {
        Transaction transaction = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM transactions ")
                    .append("WHERE ")
                    .append(" transactionID = ").append("'").append(transactionId).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {
                transaction = new Transaction();
                transaction.setTransactionID(res.getInt("transactionID"));
                transaction.setAmount(res.getFloat("amount"));
                transaction.setDate(res.getDate("date"));
                transaction.setCustomerAccount_no(res.getInt("customer_account_no"));
                transaction.setCustomerName(res.getString("customer_name"));
                transaction.setDealerAccount_no(res.getInt("dealer_account_no"));
                transaction.setDealerName(res.getString("dealer_name"));
            } else {
                System.out.println("Transaction with transaction id " + transactionId + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return transaction;
    }

    public static Civilian getCivilian2(int account_num) throws ClassNotFoundException, SQLException {
        Civilian civilian = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM civilians ")
                    .append("WHERE ")
                    .append("account_no = ").append("'").append(account_num).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {
                civilian = new Civilian();
                civilian.setUsername(res.getString("username"));
                civilian.setPassword(res.getString("password"));
                civilian.setName(res.getString("name"));
                civilian.setAccount_no(res.getInt("account_no"));
                civilian.setDebt(res.getFloat("debt"));
                civilian.setExpiration_date(res.getDate("expiration_date"));
                civilian.setBalance(res.getFloat("balance"));
                civilian.setCredit_limit(res.getInt("credit_limit"));

            } else {
                System.out.println("Civilian with account_no " + account_num + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return civilian;
    }
    public static String insert_Transaction(int transactionID, String dealerName, int dealerAccount_no, String customerName, int customerAccount_no, java.sql.Date date, double amount, String type) throws ClassNotFoundException, SQLException {

        String msg = "";
        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("INSERT INTO transactions")
                    .append("(transactionID, dealerName, dealerAccount_no, customerName, customerAccount_no, date, amount, type) ")
                    .append("VALUES (?,?,?,?,?,?,?,?)");

            PreparedStatement preparedStmt = con.prepareStatement(insQuery.toString());


            preparedStmt.setInt(1, transactionID);
            preparedStmt.setString(2, dealerName);
            preparedStmt.setInt(3, dealerAccount_no);
            preparedStmt.setString(4, customerName);
            preparedStmt.setInt(5, customerAccount_no);
            preparedStmt.setDate(6, date);
            preparedStmt.setDouble(7, amount);
            preparedStmt.setString(8, type);

            preparedStmt.execute();

            msg = "Transaction Inserted Succesfully";

        } catch (SQLException ex) {
            msg = ex.getMessage();
            // Log exception
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }
        return msg;
    }

    public static String check_Civilian_username(String username) throws ClassNotFoundException, SQLException {

        Statement stmt = null;
        Connection con = null;

        String check_username = "";
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT username FROM civilians ")
                    .append("WHERE ")
                    .append("username = ").append("'").append(username).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {

                check_username = res.getString("username");


            } else {
                System.out.println("Civilian with user name " + username + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return check_username;
    }
}
