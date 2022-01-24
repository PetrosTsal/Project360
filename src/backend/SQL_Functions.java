package backend;

import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL_Functions {

    static int tran_id = 1;


    public static void purchase(int account_num_d , int account_num_cus , float agora) throws SQLException, ClassNotFoundException {
        Civilian civ = new Civilian();
        Company comp = new Company();
        User cust = new User() ;
        cust = User.getUser2(account_num_cus);
        Dealer deal = new Dealer();
        deal = Dealer.getDealer2(account_num_d);
        String tipos = cust.getType();
        float diathesimo;
        if (tipos.equals("Civilian")){
            civ = civ.getCivilian2(account_num_cus);
            diathesimo = civ.getBalance();

            System.out.println(civ.getName() + diathesimo);
            if ( diathesimo >= agora){
                civ.setBalance(diathesimo-agora);
                //update me sql ton pinaka civillian me ta nea stoixeia
            }else{
                System.out.println("Transaction cannot happen , because of not enough Civillian's balance.");
            }
        }else if ( tipos.equals("Company")){
            comp = comp.getCompany2(account_num_cus);
            diathesimo = comp.getBalance() ;
            if ( diathesimo >= agora){
                comp.setBalance(diathesimo-agora);
                //update me sql ton pinaka company me ta nea stoixeia
            }else{
                System.out.println("Transaction cannot happen , because of not enough Company's balance.");
            }
        }

        float updated_earnings = deal.getEarnings() + agora;
        deal.setEarnings(updated_earnings);

        String msg = "";
        Statement stmt = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();
            stmt2 = con.createStatement();
            stmt3 = con.createStatement();

            StringBuilder insQuery = new StringBuilder();
            StringBuilder insQuery2 = new StringBuilder();
            StringBuilder insQuery3 = new StringBuilder();

            PreparedStatement preparedStmt, preparedStmt2, prepareStmt3;

            if(tipos.equals("Civilian")){
                insQuery.append("UPDATE civilians ");
                insQuery.append(" SET balance = ").append(civ.getBalance());
                insQuery.append(" WHERE account_no = ").append(account_num_cus);


            }else if(tipos.equals("Company")){
                insQuery.append("UPDATE companies ");
                insQuery.append(" SET balance = ").append(comp.getBalance());
                insQuery.append(" WHERE account_no = ").append(account_num_cus);
            }

            insQuery2.append("UPDATE dealers ");
            insQuery2.append(" SET earnings = ").append(deal.getEarnings());
            insQuery2.append(" WHERE account_no = ").append(account_num_d);



            preparedStmt = con.prepareStatement(insQuery.toString());
            preparedStmt.execute();

            preparedStmt2 = con.prepareStatement(insQuery2.toString());
            preparedStmt2.execute();

            msg = "Balance updated Succesfully";

        } catch (SQLException ex) {
            msg = ex.getMessage();
            // Log exception
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
            DB.closeConnection(stmt2, con);
        }

        java.sql.Date dat = new Date(2022);
        Transaction.insert_Transaction(++tran_id, deal.getName(), deal.getAccount_no(), cust.getName(), cust.getAccount_no(), dat, agora, "charge/credit");

        return ;
    }


    public static void return_things(int transs_id) throws SQLException, ClassNotFoundException {
        Transaction tra = new Transaction()  ;

        Statement stmt = null;
        Connection con = null;

        try {

            con = DB.getConnection();

            stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM transactions ")
                    .append("WHERE ")
                    .append(" transaction_id = ").append(transs_id);


            stmt.executeQuery(insQuery.toString());

            ResultSet res = stmt.getResultSet();
            //-----------------------den eixa database kai den kserw onomata sthlwn
            if (res.next() == true) {
                tra.setTransactionID(res.getInt("transaction_id"));
                tra.setDealerName(res.getString("dealer_name"));
                tra.setDealerAccount_no(res.getInt("dealer_account_no"));
                tra.setCustomerName(res.getString("cus_account_name"));
                tra.setCustomerAccount_no(res.getInt("cus_account_no"));
                tra.setDate(res.getDate("date"));
                tra.setAmount(res.getFloat("amount"));

            } else {
                tra = null;
                System.out.println("Transaction with transaction_id " + transs_id + "was not found");
            }
        } catch (SQLException ex) {
            // Log exception
            //Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
        }

        if ( tra != null ){
            User us = new User();
            Dealer deal = new Dealer() ;
            String tipos ;
            int c_acc = tra.getCustomerAccount_no() , d_acc = tra.getDealerAccount_no() ;
            double epistrofi = tra.getAmount() ;
            float balance_now = 0 , earnings_now = 0 ;
            us = User.getUser2(c_acc);
            deal = Dealer.getDealer2(d_acc);
            tipos = us.getType();
            if ( tipos.equals("Civilian")){
                Civilian civ = new Civilian();
                civ = Civilian.getCivilian2(c_acc);
                balance_now = civ.getBalance();
                civ.setBalance((float) (balance_now+epistrofi));//mallon tha xreiastei to amount tou transaction na ginei float
            }else if ( tipos.equals("Company")){
                Company comp = new Company();
                comp = Company.getCompany2(c_acc);
                balance_now = comp.getBalance();
                comp.setBalance((float) (balance_now+epistrofi));//mallon tha xreiastei to amount tou transaction na ginei float
            }
            earnings_now = deal.getEarnings();
            deal.setEarnings((float) (earnings_now-epistrofi));


            Statement stmt2 = null , stmt3 = null , stmt4 = null;
            Connection con2 = null;
            try{
                con2 = DB.getConnection();
                stmt2 = con2.createStatement();
                stmt3 = con2.createStatement();
                StringBuilder insQuery2 = new StringBuilder() , insQuery3 = new StringBuilder() , insQuery4 = new StringBuilder();
                PreparedStatement preparedStmt2 , preparedStmt3 , preparedStmt4;

                if ( tipos.equals("Civilian")){
                    insQuery2.append("UPDATE civilians ");
                    insQuery2.append(" SET balance = ").append((balance_now+epistrofi));
                    insQuery2.append(" WHERE account_no = ").append(c_acc);
                }else if ( tipos.equals("Company")){
                    insQuery2.append("UPDATE companies ");
                    insQuery2.append(" SET balance = ").append((balance_now+epistrofi));
                    insQuery2.append(" WHERE account_no = ").append(c_acc);
                }

                insQuery3.append("UPDATE dealers ");
                insQuery3.append(" SET earnings = ").append((earnings_now-epistrofi));
                insQuery3.append(" WHERE account_no = ").append(d_acc);

                insQuery4.append("DELETE FROM transactions ");
                insQuery4.append(" WHERE transaction_id = ").append(transs_id);

                preparedStmt2 = con2.prepareStatement(insQuery2.toString());
                preparedStmt2.execute();
                preparedStmt3 =  con2.prepareStatement(insQuery3.toString());
                preparedStmt3.execute();
                preparedStmt4 =  con2.prepareStatement(insQuery4.toString());
                preparedStmt4.execute();
                System.out.println("Return of product with transaction id : "+transs_id +"completed successfully.");
            } finally {
                // close connection
                DB.closeConnection(stmt2, con2);
            }

        }


    }

    //leitourgia 5
    public static void pay_debt(int account_num) throws SQLException, ClassNotFoundException {
        User us = new User();
        us = User.getUser2(account_num) ;
        String tipos = us.getType();
        if ( tipos.equals("Civilian")){
            Civilian civ = new Civilian() ;
            civ = civ.getCivilian2(account_num);
            civ.setDebt(0);
        }else if ( tipos.equals("Company")){
            Company comp = new Company() ;
            comp = comp.getCompany2(account_num);
            comp.setDebt(0);
        }else if ( tipos.equals("Dealer")){
            Dealer deal = new Dealer() ;
            deal = deal.getDealer2(account_num);
            deal.setDebt(0);
        }

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


            PreparedStatement preparedStmt , preparedStmt2;

            if(tipos.equals("Civilian")){
                insQuery.append("UPDATE civilians ");
                insQuery.append(" SET debt = ").append(0);
                insQuery.append(" WHERE account_no = ").append(account_num);


            }else if(tipos.equals("Company")){
                insQuery.append("UPDATE companies ");
                insQuery.append(" SET debt = ").append(0);
                insQuery.append(" WHERE account_no = ").append(account_num);

            }else if(tipos.equals("Dealer")) {
                insQuery.append("UPDATE dealers ");
                insQuery.append(" SET debt = ").append(0);
                insQuery.append(" WHERE account_no = ").append(account_num);
            }
            //-------------- mporei oi users na ginontai update apo ta apo panw queries na to doume
            insQuery2.append("UPDATE users ");
            insQuery2.append(" SET debt = ").append(0);
            insQuery2.append(" WHERE account_no = ").append(account_num);

            preparedStmt = con.prepareStatement(insQuery.toString());
            preparedStmt.execute();
            preparedStmt2 = con.prepareStatement(insQuery2.toString());
            preparedStmt2.execute();


            msg = "Debt paid succesfully for the  " +tipos +".";

        } catch (SQLException ex) {
            msg = ex.getMessage();
            // Log exception
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close connection
            DB.closeConnection(stmt, con);
            DB.closeConnection(stmt2, con);
        }
        System.out.println(msg);
        return ;
    }

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

            insQuery.append("SELECT TOP 1 dealerAccount_no");
            insQuery.append(" FROM transactions");
            insQuery.append(" GROUP BY dealerAccount_no");
            insQuery.append(" ORDER BY Count(dealerAccount_no) DESC");

            stmt.executeQuery(insQuery.toString());
            ResultSet res = stmt.getResultSet();

            if(res.next()) {
                int bestDealer_accountNo = res.getInt("dealerAccount_no");

                insQuery2.append("SELECT debt");
                insQuery2.append(" FROM dealers");
                insQuery2.append(" WHERE dealerAccount_no = ").append(bestDealer_accountNo);

                stmt2.executeQuery(insQuery2.toString());
                ResultSet res2 = stmt2.getResultSet();

                if(res2.next()) {
                    double new_debt = res.getDouble("debt");
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
