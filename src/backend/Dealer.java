package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dealer extends User{
    // attributes
    private double commission;
    private double earnings;

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public Dealer(){
        setType("Dealer");
    }

    public Dealer(String username, String password, String name, int account_no, double debt, double commission, double earnings) {
        super(username, password, name, account_no, debt);
        this.commission = commission;
        this.earnings = earnings;
        setType("Dealer");
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    public String getType() {
        return "Dealer";
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
                    .append("account_no = ").append("'").append(account_num).append("'");

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




    // functionsabhbshbh
}
