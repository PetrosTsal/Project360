package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dealer extends User{
    // attributes
    private float commission;
    private float earnings;

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public float getEarnings() {
        return earnings;
    }

    public void setEarnings(float earnings) {
        this.earnings = earnings;
    }

    public Dealer(){

    }

    public Dealer(String username, String password, String name, int account_no, float debt, float commission, float earnings,String type ) {
        super(username, password, name, account_no, debt , type);
        this.commission = commission;
        this.earnings = earnings;
    }
    public static Dealer getDealer(String username , String password) throws ClassNotFoundException, SQLException {
        Dealer dealer = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM dealers ")
                    .append("WHERE ")
                    .append(" username = ").append("'").append(username).append("AND password = ").append("'").append(password).append("'");


            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {
                dealer = new Dealer();
                dealer.setUsername(res.getString("username"));
                dealer.setPassword(res.getString("password"));
                dealer.setName(res.getString("name"));
                dealer.setAccount_no(res.getInt("account_no"));
                dealer.setDebt(res.getFloat("debt"));
                dealer.setCommission(res.getFloat("commission"));
                dealer.setEarnings(res.getFloat("earnings"));

            } else {
                System.out.println("Dealer with username " + username + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return dealer;
    }

    public static Dealer getDealer2(int account_num) throws ClassNotFoundException, SQLException {
        Dealer dealer = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM dealers ")
                    .append("WHERE ")
                    .append("Username = ").append("'").append(account_num).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {
                dealer = new Dealer();
                dealer.setUsername(res.getString("username"));
                dealer.setPassword(res.getString("password"));
                dealer.setName(res.getString("name"));
                dealer.setAccount_no(res.getInt("account_no"));
                dealer.setDebt(res.getFloat("debt"));
                dealer.setCommission(res.getFloat("commission"));
                dealer.setEarnings(res.getFloat("earnings"));

            } else {
                System.out.println("Dealer with account_no " + account_num + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return dealer;
    }

    public static String register_Dealer(String username, String password, String name, int account_no, float debt, float commission,float earnings) throws ClassNotFoundException, SQLException {

        String msg = "";
        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("INSERT INTO dealers")
                    .append("(username, password, name, account_no, debt, commission, earnings) ")
                    .append("VALUES (?,?,?,?,?,?,?)");

            PreparedStatement preparedStmt = con.prepareStatement(insQuery.toString());


            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, name);
            preparedStmt.setInt(4, account_no);
            preparedStmt.setFloat(5, debt);
            preparedStmt.setFloat(6, commission);
            preparedStmt.setFloat(7, earnings);

            preparedStmt.execute();

            msg = "Dealer Registered Succesfully";

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

    public static String check_Dealer_username(String username) throws ClassNotFoundException, SQLException {

        Statement stmt = null;
        Connection con = null;

        String check_username = "";
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT username FROM dealers ")
                    .append("WHERE ")
                    .append("username = ").append("'").append(username).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {

                check_username = res.getString("username");


            } else {
                System.out.println("Dealer with user name " + username + "was not found");
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

    // functionsabhbshbh
}
