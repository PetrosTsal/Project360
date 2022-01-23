package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL_Functions {

    public static void unregister_user(int account_num) {
        Connection connection = null;
        Statement statement = null;

        try {
            
            connection = DB.getConnection();
            statement = connection.createStatement();

            if(Dealer.getDealer2(account_num) != null) {
                float debt = Dealer.getDealer2(account_num).getDebt();
                if(debt == 0) {
                    ResultSet resultSet = statement.executeQuery("delete from");
                }
                else {
                    System.out.println("The debt is not equal to zero.");
                }
            }
            else if(Civilian.getCivilian2(account_num) != null) {
                float debt = Civilian.getCivilian2(account_num).getDebt();
                if(debt == 0) {
                    ResultSet resultSet = statement.executeQuery("delete from");
                }
                else {
                    System.out.println("The debt is not equal to zero.");
                }
            }
            else if(Company.getCompany2(account_num) != null) {
                float debt = Company.getCompany2(account_num).getDebt();
                if(debt == 0) {
                    ResultSet resultSet = statement.executeQuery("delete from");
                }
                else {
                    System.out.println("The debt is not equal to zero");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DB.closeConnection(statement, connection);
        }
    }

    //leitourgia 3
    public static void purchase(int account_num_d , int account_num_cus , int agora) throws SQLException, ClassNotFoundException {
        User cust = new User() ;
        cust = User.getUser2(account_num_cus);
        String tipos = cust.getType();
        float diathesimo ;
        if (tipos.equals("Civillian")){
            Civilian civ = new Civilian() ;
            civ.getCivilian2(account_num_cus);
            diathesimo = civ.getBalance();
            if ( diathesimo >= agora){
                civ.setBalance(diathesimo-agora);
                //update me sql ton pinaka civillian me ta nea stoixeia
            }else{
                System.out.println("Transaction cannot happen , because of not enough Civillian's balance.");
            }
        }else if ( tipos.equals("Company")){
            Company comp = new Company() ;
            comp.getCompany2(account_num_cus);
            diathesimo = comp.getBalance() ;
            if ( diathesimo >= agora){
                comp.setBalance(diathesimo-agora);
                //update me sql ton pinaka company me ta nea stoixeia
            }else{
                System.out.println("Transaction cannot happen , because of not enough Company's balance.");
            }
        }
        //eisagwgh transaction me sinartisi pou tha kanei insert transactions h apo edw me query ??
        return ;
    }
}
