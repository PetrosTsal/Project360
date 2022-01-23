package backend;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Company extends Customer{
    // attributes
    // list of authorized personnel

    public Company() {
        setType("Company");
    }

    public Company(String username, String password, String name, int account_no, float debt, java.sql.Date expiration_date, float balance, int credit_limit) {
        super(username, password, name, account_no, debt, expiration_date, balance, credit_limit);
        setType("Company");
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    public static Company getCompany(String username , String password) throws ClassNotFoundException, SQLException {
        Company company = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM companies ")
                    .append("WHERE ")
                    .append(" username = ").append("'").append(username).append("AND password = ").append("'").append(password).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {
                company = new Company();
                company.setUsername(res.getString("username"));
                company.setPassword(res.getString("password"));
                company.setName(res.getString("name"));
                company.setAccount_no(res.getInt("account_no"));
                company.setDebt(res.getFloat("debt"));
                company.setExpiration_date(res.getDate("expiration_date"));
                company.setBalance(res.getFloat("balance"));
                company.setCredit_limit(res.getInt("credit_limit"));

            } else {
                System.out.println("Company with username " + username + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return company;
    }

    public static Company getCompany2(int account_num) throws ClassNotFoundException, SQLException {
        Company company = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM companies ")
                    .append("WHERE ")
                    .append("account_no = ").append("'").append(account_num).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {
                company = new Company();
                company.setUsername(res.getString("username"));
                company.setPassword(res.getString("password"));
                company.setName(res.getString("name"));
                company.setAccount_no(res.getInt("account_no"));
                company.setDebt(res.getFloat("debt"));
                company.setExpiration_date(res.getDate("expiration_date"));
                company.setBalance(res.getFloat("balance"));
                company.setCredit_limit(res.getInt("credit_limit"));

            } else {
                System.out.println("Company with account_no " + account_num + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        return company;
    }
    public static String register_Company(String username, String password, String name, int account_no, float debt, java.sql.Date expiration_date, float balance , int credit_limit) throws ClassNotFoundException, SQLException {

        String msg = "";
        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("INSERT INTO companies")
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

            User.register_User(username, password, name, account_no, debt, "Company");

            msg = "Company Registered Succesfully";

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

    public static String check_Company_username(String username) throws ClassNotFoundException, SQLException {

        Statement stmt = null;
        Connection con = null;

        String check_username = "";
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT username FROM companies ")
                    .append("WHERE ")
                    .append("username = ").append("'").append(username).append("'");

            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            if (res.next() == true) {

                check_username = res.getString("username");


            } else {
                System.out.println("Company with user name " + username + "was not found");
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



