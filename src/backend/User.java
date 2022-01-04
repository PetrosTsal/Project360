package backend;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    // attributes
    private String username;
    private String password;
    private String name;
    private int account_no; // primary key
    private float debt;
    // ...
    public  User(){

    }
    public User(String username , String password ,String name, int account_no, float debt) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.account_no = account_no;
        this.debt = debt;
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

    public float getDebt() {
        return debt;
    }

    public void setDebt(float debt) {
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
                    .append(" user_name = ").append("'").append(username).append("';");

            stmnt.execute(insQuery.toString());

            ResultSet res = stmnt.getResultSet();

            if (res.next() == true) {
                user = new User();
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
                user.setName(res.getString("name"));
                user.setAccount_no(res.getInt("account_no"));
                user.setDebt(res.getFloat("debt"));

            } else {
                System.out.println("User with user name " + username + "was not found");
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
// functions
}
