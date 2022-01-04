package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Company extends Customer{
    // attributes
        // list of authorized personnel

    public Company() {

    }

    public Company(String username, String password, String name, int account_no, float debt, Date expiration_date, float balance, int credit_limit) {
        super(username, password, name, account_no, debt, expiration_date, balance, credit_limit);
    }

    public static Company getCompany(String Username, String Password) throws ClassNotFoundException, SQLException {
        Company company = null;
        Statement stmt = null;
        Connection con = null;
        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM companies ")
                    .append("WHERE ")
                    .append("Username = ").append("'").append(Username).append("'").append("AND Password = ").append("'").append(Password).append("'");

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
                System.out.println("Company with username " + Username + "was not found");
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

    // functions
}
