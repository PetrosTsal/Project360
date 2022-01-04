package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Civilian extends Customer{
    // attributes


    public Civilian() {

    }

    public Civilian(String username, String password, String name, int account_no, float debt, Date expiration_date, float balance, int credit_limit) {
        super(username, password, name, account_no, debt, expiration_date, balance, credit_limit);
    }

    public static Civilian getCivilian(String Username, String Password) throws ClassNotFoundException, SQLException {
        Civilian civilian = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM civilians ")
                    .append("WHERE ")
                    .append("Username = ").append("'").append(Username).append("'").append("AND Password = ").append("'").append(Password).append("'");

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
                System.out.println("Civilian with username " + Username + "was not found");
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
// functions
}
