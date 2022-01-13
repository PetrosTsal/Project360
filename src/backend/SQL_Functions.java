package backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL_Functions {

    public static void unregister_user(String username, String password) {
        Connection connection = null;
        Statement statement = null;

        try {
            
            connection = DB.getConnection();
            statement = connection.createStatement();

            if(Dealer.getDealer(username, password) != null) {
                float debt = Dealer.getDealer(username, password).getDebt();
                if(debt == 0) {
                    ResultSet resultSet = statement.executeQuery("delete from");
                }
                else {
                    System.out.println("The debt is not equal to zero.");
                }
            }
            else if(Civilian.getCivilian(username, password) != null) {
                float debt = Civilian.getCivilian(username, password).getDebt();
                if(debt == 0) {
                    ResultSet resultSet = statement.executeQuery("delete from");
                }
                else {
                    System.out.println("The debt is not equal to zero.");
                }
            }
            else if(Company.getCompany(username, password) != null) {
                float debt = Company.getCompany(username, password).getDebt();
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
}
