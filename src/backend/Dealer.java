package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public Dealer(String username, String password, String name, int account_no, float debt, float commission, float earnings) {
        super(username, password, name, account_no, debt);
        this.commission = commission;
        this.earnings = earnings;
    }

    public static Dealer getDealer(String Username, String Password) throws ClassNotFoundException, SQLException {
        Dealer dealer = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM dealers ")
                    .append("WHERE ")
                    .append("Username = ").append("'").append(Username).append("'").append("AND Password = ").append("'").append(Password).append("'");

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
                dealer.setEarnings(res.getInt("earnings"));

            } else {
                System.out.println("Dealer with username " + Username + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            closeDBConnection(stmt, con);
        }

        return dealer;
    }

    // functions
}
