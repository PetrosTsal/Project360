package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Civilian extends Customer{
    // attributes


    public Civilian() {
        setType("Civilian");
    }

    public Civilian(String username, String password, String name, int account_no, double debt, java.sql.Date expiration_date, double balance, int credit_limit) {
        super(username, password, name, account_no, debt, expiration_date, balance, credit_limit);
        setType("Civilian");
    }

    @Override
    public void setType(String type) {
        super.setType(type);
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





    // functions
}
