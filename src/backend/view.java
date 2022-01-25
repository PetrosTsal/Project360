package backend;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class view {

    public static void user_login() throws SQLException, ClassNotFoundException {

        String username , password;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Username: ");
            username = scanner.nextLine();
            System.out.println("Password: ");
            password = scanner.nextLine();

            if(CCC.ret_Civilian(username, password) != null) {

            } else if (CCC.ret_Company(username, password) != null) {

            } else if (CCC.ret_Dealer(username, password) != null) {

            } else {
                System.out.println("Wrong username or password, please try again");
            }
        }
    }


    public static void user_register() throws SQLException, ClassNotFoundException {

        String register;
        String username, password, name;
        int account_no, credit_limit ;
        double debt, balance, commission, earnings;
        java.sql.Date expiration_date;
        Calendar cal = Calendar.getInstance();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Press a number and enter to choose:");
            System.out.println("Register as:");
            System.out.println(" 1. Civilian\n 2. Company\n 3. Dealer");

            register = scanner.nextLine();

            switch (register) {
                case "1":
                    System.out.println("New username: ");
                    username = scanner.nextLine();
                    while(Civilian.getCivilianByUsername(username) != null){
                        System.out.println("Not available username");
                        System.out.println("New username: ");
                        username = scanner.nextLine();
                    }

                    System.out.println("New password: ");
                    password = scanner.nextLine();

                    System.out.println("Name: ");
                    name = scanner.nextLine();

                    System.out.println("Give me your account_number please");
                    account_no = scanner.nextInt();

                    debt = 0;

                    cal.add(Calendar.YEAR, 4);
                    expiration_date = new java.sql.Date(cal.getTimeInMillis());

                    System.out.println("Balance");
                    balance = scanner.nextDouble();
                    while(balance < 0) {
                        System.out.println("Give non-negative balance please");
                        System.out.println("Balance");
                        balance = scanner.nextDouble();
                    }

                    System.out.println("Credit-limit:");
                    credit_limit = scanner.nextInt();
                    while(credit_limit < 0) {
                        System.out.println("Give non-negative credit limit please");
                        System.out.println("Credit-limit");
                        credit_limit = scanner.nextInt();
                    }

                    Civilian civ = new Civilian();
                    System.out.println(CCC.register_Civilian(username, password, name, account_no, debt, expiration_date, balance, credit_limit));
                    return;
                case "2":
                    System.out.println("New username: ");
                    username = scanner.nextLine();
                    while(Civilian.getCivilianByUsername(username) != null){
                        System.out.println("Not available username");
                        System.out.println("New username: ");
                        username = scanner.nextLine();
                    }

                    System.out.println("New password: ");
                    password = scanner.nextLine();

                    System.out.println("Name: ");
                    name = scanner.nextLine();

                    System.out.println("Give me your account_number please");
                    account_no = scanner.nextInt();

                    debt = 0;

                    cal.add(Calendar.YEAR, 4);
                    expiration_date = new java.sql.Date(cal.getTimeInMillis());

                    expiration_date = new java.sql.Date(System.currentTimeMillis());

                    System.out.println("Balance");
                    balance = scanner.nextDouble();
                    while(balance < 0) {
                        System.out.println("Give non-negative balance please");
                        System.out.println("Balance");
                        balance = scanner.nextDouble();
                    }

                    System.out.println("Credit-limit:");
                    credit_limit = scanner.nextInt();
                    while(credit_limit < 0) {
                        System.out.println("Give non-negative credit limit please");
                        System.out.println("Credit-limit");
                        credit_limit = scanner.nextInt();
                    }

                    Company comp = new Company();
                    System.out.println(CCC.register_Company(username, password, name, account_no, debt, expiration_date, balance, credit_limit));
                    return;
                case "3":
                    System.out.println("New username: ");
                    username = scanner.nextLine();
                    while(Civilian.getCivilianByUsername(username) != null){
                        System.out.println("Not available username");
                        System.out.println("New username: ");
                        username = scanner.nextLine();
                    }

                    System.out.println("New password: ");
                    password = scanner.nextLine();

                    System.out.println("Name: ");
                    name = scanner.nextLine();

                    System.out.println("Give me your account_number please");
                    account_no = scanner.nextInt();

                    debt = 0;

                    System.out.println("Commission: ");
                    commission = scanner.nextDouble();
                    while (commission < 0) {
                        System.out.println("Give non-negative commission please");
                        System.out.println("Commission: ");
                        commission = scanner.nextDouble();
                    }

                    System.out.println("Earnings: ");
                    earnings = scanner.nextFloat();
                    while (earnings < 0) {
                        System.out.println("Give non-negative earnings please");
                        System.out.println("Earnings: ");
                        earnings = scanner.nextFloat();
                    }

                    Dealer deal = new Dealer();
                    System.out.println(CCC.register_Dealer(username, password, name, account_no, debt, commission, earnings));
                    return;
                default:
                    System.out.println("Choose a valid number, type it and press enter please");
                    break;
            }
        }
    }


    public static void employee_login(){


    }


    public static void initial_menu() throws SQLException, ClassNotFoundException {

        while (true) {
            System.out.println("Press a number and enter to choose:");
            System.out.println(" 1. Login User\n 2. Register User\n 3. Login Employee\n 4. Exit");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    user_login();
                    break;
                case 2:
                    user_register();
                    break;
                case 3:
                    //employee_login();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Choose a valid number, type it and press enter please");
            }
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("Welcome to CCC.");

        initial_menu();

        return;
    }
}
