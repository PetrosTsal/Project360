package backend;

import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL_Functions {

    static int tran_id = 1;

    //leitourgia 3

//    public static void unregister_user(int account_num) {
//        Connection connection = null;
//        Statement statement = null;
//
//        try {
//
//            connection = DB.getConnection();
//            statement = connection.createStatement();
//
//            if(Dealer.getDealer2(account_num) != null) {
//                float debt = Dealer.getDealer2(account_num).getDebt();
//                if(debt == 0) {
//                    ResultSet resultSet = statement.executeQuery("delete from");
//                }
//                else {
//                    System.out.println("The debt is not equal to zero.");
//                }
//            }
//            else if(Civilian.getCivilian2(account_num) != null) {
//                float debt = Civilian.getCivilian2(account_num).getDebt();
//                if(debt == 0) {
//                    ResultSet resultSet = statement.executeQuery("delete from");
//                }
//                else {
//                    System.out.println("The debt is not equal to zero.");
//                }
//            }
//            else if(Company.getCompany2(account_num) != null) {
//                float debt = Company.getCompany2(account_num).getDebt();
//                if(debt == 0) {
//                    ResultSet resultSet = statement.executeQuery("delete from");
//                }
//                else {
//                    System.out.println("The debt is not equal to zero");
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            DB.closeConnection(statement, connection);
//        }
//    }
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
}
