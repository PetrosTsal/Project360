package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Civilian extends Customer{
    // attributes


    public Civilian() {
        setType("Civilian");
    }

    public Civilian(String username, String password, String name, int account_no, float debt, java.sql.Date expiration_date, float balance, int credit_limit) {
        super(username, password, name, account_no, debt, expiration_date, balance, credit_limit);
        setType("Civilian");
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    public static Civilian getCivilian(String username , String password) throws ClassNotFoundException, SQLException {
        Civilian civilian = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM civilians ")
                    .append("WHERE ")
                    .append(" username = ").append("'").append(username).append("AND password = ").append("'").append(password).append("'");

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
                System.out.println("Civilian with username " + username + "was not found");
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

    public static Civilian getCivilian2(int account_num) throws ClassNotFoundException, SQLException {
        Civilian civilian = new Civilian();
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

    public static String register_Civilian(String username, String password, String name, int account_no, float debt, java.sql.Date expiration_date, float balance , int credit_limit) throws ClassNotFoundException, SQLException {

        String msg = "";
        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("INSERT INTO civilians")
                    .append("(username, password, name, account_no, debt, expiration_date, balance, credit_limit) ")
                    .append("VALUES (?,?,?,?,?,?,?,?)");

            PreparedStatement preparedStmt = con.prepareStatement(insQuery.toString());


            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, name);
            preparedStmt.setInt(4, account_no);
            preparedStmt.setFloat(5, debt);
            preparedStmt.setDate(6, expiration_date);
            preparedStmt.setFloat(7, balance);
            preparedStmt.setInt(8, credit_limit);

            preparedStmt.execute();

            User.register_User(username, password, name, account_no, debt, "Civilian");

            msg = "Civilian Registered Succesfully";

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

    // functions
}
