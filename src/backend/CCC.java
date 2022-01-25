package backend;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CCC {
    // attributes
    // functions
        //--------- arxizei login-----------------------------------------------//
        //--------- prwta tsekaroume an uparxei user me auto to username , pass an uparxei pairnoume tipo tou kai //
        //--------kanoume katallilo login me thn ret_(oti tipos einai)-------------------------------------//
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


   public static Civilian ret_Civilian(String username , String password){
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

    public static Company ret_Company(String username , String password) throws ClassNotFoundException, SQLException {
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
                company.setExpiration_date(res.getDate("exp_date"));
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

    public static Dealer ret_Dealer(String username , String password)throws ClassNotFoundException, SQLException {
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

    //----telos login-----------------------------------------------//

    //--------------arxizei register--------------------------------//
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

            msg="Civilian Registered Succesfully";

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

    public static String register_Company(String username, String password, String name, int account_no, float debt, java.sql.Date expiration_date, float balance , int credit_limit) throws ClassNotFoundException, SQLException {

        String msg = "";
        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("INSERT INTO companies")
                    .append("(username, password, name, account_no, debt, exp_date, balance, credit_limit) ")
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

            User.register_User(username, password, name, account_no, debt, "Dealer");

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
    //------------------telos-register---------------------------------------//

    //-------------------kleisomo logariasmou------------------------------//
    public static String unregister_User(int account_num) {
        String msg = "";
        Statement stmt = null;
        Connection con = null;
        String type;
        double debt;

        try {
            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();
            StringBuilder insQuery2 = new StringBuilder();
            StringBuilder insQuery3 = new StringBuilder();

            insQuery.append("SELECT type,debt FROM users");
            insQuery.append(" WHERE account_no = ").append(account_num);

            stmt.executeQuery(insQuery.toString());
            ResultSet res = stmt.getResultSet();

            if(res.next()) {
                type = res.getString("type");
                debt = res.getDouble("debt");

                if(type.equals("Civilian")) {
                    if(debt == 0) {
                        insQuery2.append("DELETE FROM civilians");
                        insQuery2.append(" WHERE account_no = ").append(account_num);

                        PreparedStatement pstmt = con.prepareStatement(insQuery2.toString());
                        pstmt.execute();

                        insQuery3.append("DELETE FROM users");
                        insQuery3.append(" WHERE account_no = ").append(account_num);

                        PreparedStatement pstmt2 = con.prepareStatement(insQuery3.toString());
                        pstmt2.execute();
                    }
                    else {
                        System.out.println("Error, this user still has debt.");
                    }
                }
                else if(type.equals("Company")) {
                    if(debt == 0) {
                        insQuery2.append("DELETE FROM companies");
                        insQuery2.append(" WHERE account_no = ").append(account_num);

                        PreparedStatement pstmt = con.prepareStatement(insQuery2.toString());
                        pstmt.execute();

                        insQuery3.append("DELETE FROM users");
                        insQuery3.append(" WHERE account_no = ").append(account_num);

                        PreparedStatement pstmt2 = con.prepareStatement(insQuery3.toString());
                        pstmt2.execute();
                    }
                    else {
                        System.out.println("Error, this user still has debt.");
                    }
                }
                else if(type.equals("Dealer")) {
                    if(debt == 0) {
                        insQuery2.append("DELETE FROM dealers");
                        insQuery2.append(" WHERE account_no = ").append(account_num);

                        PreparedStatement pstmt = con.prepareStatement(insQuery2.toString());
                        pstmt.execute();

                        insQuery3.append("DELETE FROM users");
                        insQuery3.append(" WHERE account_no = ").append(account_num);

                        PreparedStatement pstmt2 = con.prepareStatement(insQuery3.toString());
                        pstmt2.execute();
                    }
                    else {
                        System.out.println("Error, this user still has debt.");
                    }
                }
                else {
                    System.out.println("No such type.");
                }
            }
        } catch (SQLException ex) {
            msg = ex.getMessage();
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.closeConnection(stmt, con);
        }

        return msg;
    }

    //-----------------------telos kleisimou logariasmou -----------------------------//
    //----------------------stoixeia kalou - kakou xrhsth kai emporos toumhna------------//
    public static void dealer_of_the_month() {
        String msg = "";
        Statement stmt = null;
        Statement stmt2 = null;
        Connection con = null;

        try {
            con = DB.getConnection();

            stmt = con.createStatement();
            stmt2 = con.createStatement();

            StringBuilder insQuery = new StringBuilder();
            StringBuilder insQuery2 = new StringBuilder();
            StringBuilder insQuery3 = new StringBuilder();

            PreparedStatement pstmt = null;

            insQuery.append("SELECT dealerAccount_no");
            insQuery.append(" FROM transactions");
            insQuery.append(" GROUP BY dealerAccount_no");
            insQuery.append(" ORDER BY COUNT(dealerAccount_no) DESC");
            insQuery.append(" LIMIT 1");

            stmt.executeQuery(insQuery.toString());
            ResultSet res = stmt.getResultSet();

            if(res.next()) {
                int bestDealer_accountNo = res.getInt("dealerAccount_no");

                insQuery2.append("SELECT debt");
                insQuery2.append(" FROM dealers");
                insQuery2.append(" WHERE account_no = ").append(bestDealer_accountNo);

                stmt2.executeQuery(insQuery2.toString());
                ResultSet res2 = stmt2.getResultSet();

                if(res2.next()) {
                    double new_debt = res2.getDouble("debt");
                    if(new_debt != 0) {
                        new_debt = new_debt - (new_debt*0.05);

                        insQuery3.append("UPDATE dealers");
                        insQuery3.append(" SET debt = ").append(new_debt);
                        insQuery3.append(" WHERE account_no = ").append(bestDealer_accountNo);

                        pstmt = con.prepareStatement(insQuery3.toString());
                        pstmt.execute();
                    }
                    else {
                        System.out.println("This dealer has no debt to the CCC.");
                    }
                }
            }
        } catch (SQLException ex) {
            msg = ex.getMessage();
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DB.closeConnection(stmt, con);
        }
    }

    public static void getGoldUsers(){

        Connection connection = null;
        Statement statement = null;

        Map<Integer, String> goldMap = new HashMap<Integer, String>();

        try {

            connection = DB.getConnection();
            statement = connection.createStatement();

            User user;

            StringBuilder insQueryCiv = new StringBuilder();
            StringBuilder insQueryCom = new StringBuilder();
            StringBuilder insQueryDeal = new StringBuilder();

            insQueryCiv.append("SELECT * FROM civilians ")
                    .append("WHERE ")
                    .append("debt = 0");

            insQueryCom.append("SELECT * FROM companies ")
                    .append("WHERE ")
                    .append("debt = 0");

            insQueryDeal.append("SELECT * FROM dealers ")
                    .append("WHERE ")
                    .append("debt = 0");

            statement.executeQuery(insQueryCiv.toString());

            ResultSet resCiv = statement.getResultSet();

            while(resCiv.next()){
                goldMap.put(resCiv.getInt("account_no") ,resCiv.getString("name"));
            }

            statement.executeQuery(insQueryCom.toString());

            ResultSet resCom = statement.getResultSet();

            while(resCom.next()){
                goldMap.put(resCom.getInt("account_no") ,resCom.getString("name"));
            }

            statement.executeQuery(insQueryDeal.toString());

            ResultSet resDeal = statement.getResultSet();

            while(resDeal.next()){
                goldMap.put(resDeal.getInt("account_no") ,resDeal.getString("name"));
            }

            for (Map.Entry<Integer, String> entry : goldMap.entrySet()) {
                System.out.println(entry.getKey() + " | " + entry.getValue());
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DB.closeConnection(statement, connection);
        }

        return;
    }


    public static void getStandardUsers(){

        Connection connection = null;
        Statement statement = null;

        Map<Integer, String> goldMap = new HashMap<Integer, String>();

        try {

            connection = DB.getConnection();
            statement = connection.createStatement();

            User user;

            StringBuilder insQueryCiv = new StringBuilder();
            StringBuilder insQueryCom = new StringBuilder();
            StringBuilder insQueryDeal = new StringBuilder();

            insQueryCiv.append("SELECT * FROM civilians ")
                    .append("WHERE ")
                    .append("debt > 0");

            insQueryCom.append("SELECT * FROM companies ")
                    .append("WHERE ")
                    .append("debt > 0");

            insQueryDeal.append("SELECT * FROM dealers ")
                    .append("WHERE ")
                    .append("debt > 0");

            statement.executeQuery(insQueryCiv.toString());

            ResultSet resCiv = statement.getResultSet();

            while(resCiv.next()){
                System.out.println(resCiv.getString("name"));
                goldMap.put(resCiv.getInt("account_no") ,resCiv.getString("name"));
            }

            statement.executeQuery(insQueryCom.toString());

            ResultSet resCom = statement.getResultSet();

            while(resCom.next()){
                goldMap.put(resCom.getInt("account_no") ,resCom.getString("name"));
            }

            statement.executeQuery(insQueryDeal.toString());

            ResultSet resDeal = statement.getResultSet();

            while(resDeal.next()){
                goldMap.put(resDeal.getInt("account_no") ,resDeal.getString("name"));
            }

            for (Map.Entry<Integer, String> entry : goldMap.entrySet()) {
                System.out.println(entry.getKey() + " | " + entry.getValue());
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DB.closeConnection(statement, connection);
        }

        return;
    }

}
