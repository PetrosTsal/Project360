package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    // attributes
    private String username;
    private String password;
    private String name;
    private int account_no; // primary key
    private double debt;
    private String type ;
    // ...

    public  User(){
        this.type = "User";
    }

    public User(String username , String password ,String name, int account_no, double debt) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.account_no = account_no;
        this.debt = debt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccount_no() {
        return account_no;
    }

    public void setAccount_no(int account_no) {
        this.account_no = account_no;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public static User getUser(String username , String password) throws ClassNotFoundException , SQLException {
        User user = null ;
        Statement stmnt = null;
        Connection con = null;

        try {
            con = DB.getConnection();

            stmnt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM users ")
                    .append(" WHERE ")
                    .append(" username = ").append("'").append(username).append("AND password = ").append("'").append(password).append("'");

            stmnt.execute(insQuery.toString());

            ResultSet res = stmnt.getResultSet();

            if (res.next() == true) {
                user = new User();
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setName(res.getString("name"));
                user.setAccount_no(res.getInt("account_no"));
                user.setDebt(res.getFloat("debt"));
                user.setType(res.getString("type"));

            } else {
                System.out.println("User with username " + username + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            //closeDBConnection(stmt, con);
        }

        return user;
    }

    public static User getUser2(int account_num) throws ClassNotFoundException , SQLException {
        User user = null ;
        Statement stmnt = null;
        Connection con = null;

        try {
            con = DB.getConnection();

            stmnt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM users ")
                    .append(" WHERE ")
                    .append(" account_no = ").append("'").append(account_num).append("';");

            stmnt.execute(insQuery.toString());

            ResultSet res = stmnt.getResultSet();

            if (res.next() == true) {
                user = new User();
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setName(res.getString("name"));
                user.setAccount_no(res.getInt("account_no"));
                user.setDebt(res.getFloat("debt"));
                user.setType(res.getString("type"));

            } else {
                System.out.println("User with account_no " + account_num + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            //closeDBConnection(stmt, con);
        }

        return user;
    }

    public static String register_User(String username, String password, String name, int account_no, float debt, String type) throws ClassNotFoundException, SQLException {

        String msg = "";
        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("INSERT INTO users")
                    .append("(username, password, name, account_no, debt, type) ")
                    .append("VALUES (?,?,?,?,?,?)");

            PreparedStatement preparedStmt = con.prepareStatement(insQuery.toString());



            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, name);
            preparedStmt.setInt(4, account_no);
            preparedStmt.setFloat(5, debt);
            preparedStmt.setString(6, type);

            preparedStmt.execute();

            msg = "User Registered Succesfully";

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
}
