package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class SQL_Functions {

    public static void register_user(User user) {

        try {

            // Get the general information of a user
            String username = user.getUsername();
            String password = user.getPassword();
            String name = user.getName();
            int account_no = user.getAccount_no();
            float debt = user.getDebt();

            // Establish a connection with the database
            Connection connection = DriverManager.getConnection();

            // Creates a statement that can interact with the database
            Statement statement = connection.createStatement();

            // If the user is a dealer
            if(user instanceof Dealer) {

                // Get the specific dealer information
                float comission = ((Dealer) user).getCommission();
                float earnings = ((Dealer) user).getEarnings();

                // Execute the query and store the result
                ResultSet resultSet = statement.executeQuery("insert into" +
                        "");
            }
            // If the user is a customer
            else if(user instanceof Customer) {

                // Get the specific customer information
                Date expiration_date = ((Customer) user).getExpiration_date();
                float balance = ((Customer) user).getBalance();
                int credit_limit = ((Customer) user).getCredit_limit();

                // Execute the query and store the result
                ResultSet resultSet = statement.executeQuery("insert into" +
                        "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregister_user(String username, String password) {

        try {

            Connection connection = DriverManager.getConnection();

            Statement statement = connection.createStatement();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
