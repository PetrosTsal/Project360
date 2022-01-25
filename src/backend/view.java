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
            Civilian civilian = CCC.ret_Civilian(username, password);
            Company company = CCC.ret_Company(username, password);
            Dealer dealer = CCC.ret_Dealer(username, password);

            if(civilian != null) {
                while(true) {
                    System.out.println("Press a number and enter to choose:");
                    System.out.println("Actions:");
                    System.out.println(" 1. Make purchase\n 2. Make a return\n 3. Make payment\n 4. Show Transactions\n  5. Unregister\n 6. Exit");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Dealer Account_no: ");
                            int dealerId = scanner.nextInt();
                            while (Dealer.getDealer2(dealerId) == null) {
                                System.out.println("Not a valid Dealer Account_no, please try again");
                                System.out.println("Dealer Account_no: ");
                                dealerId = scanner.nextInt();
                            }

                            System.out.println("Amount: ");
                            int amount = scanner.nextInt();
                            while (amount <= 0) {
                                System.out.println("Not a valid amount, please give a positive amount");
                                System.out.println("Amount: ");
                                amount = scanner.nextInt();
                            }

                            SQL_Functions.purchase(dealerId, civilian.getAccount_no(), amount);
                            break;
                        case 2:
                            System.out.println("Transaction Id: ");
                            int transaction_id = scanner.nextInt();
                            Transaction transaction = Transaction.getTransaction(transaction_id);
                            if (transaction == null || transaction.getCustomerAccount_no() != civilian.getAccount_no()) {
                                System.out.println("There is no such a transaction");
                                break;
                            }
                            SQL_Functions.return_things(transaction_id);
                            break;
                        case 3:
                            if (civilian.getBalance() >= civilian.getDebt())
                                SQL_Functions.pay_debt(civilian.getAccount_no());
                            else
                                System.out.println("Your balance is not enough");
                            break;
                        case 4:
                            SQL_Functions.other_questions2b(civilian.getAccount_no());
                            break;
                        case 5:
                            CCC.unregister_User(civilian.getAccount_no());
                            return;
                        case 6:
                            return;
                        default:
                            System.out.println("Choose a valid number, type it and press enter please");
                            break;
                    }
                }
            } else if (company != null) {
                while (true) {
                    System.out.println("Press a number and enter to choose:");
                    System.out.println("Actions:");
                    System.out.println(" 1. Make purchase\n 2. Make a return\n 3. Make payment");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Dealer Account_no: ");
                            int dealerId = scanner.nextInt();
                            while (Dealer.getDealer2(dealerId) == null) {
                                System.out.println("Not a valid Dealer Account_no, please try again");
                                System.out.println("Dealer Account_no: ");
                                dealerId = scanner.nextInt();
                            }

                            System.out.println("Amount: ");
                            int amount = scanner.nextInt();
                            while (amount <= 0) {
                                System.out.println("Not a valid amount, please give a positive amount");
                                System.out.println("Amount: ");
                                amount = scanner.nextInt();
                            }

                            SQL_Functions.purchase(dealerId, company.getAccount_no(), amount);
                            break;
                        case 2:
                            System.out.println("Transaction Id: ");
                            int transaction_id = scanner.nextInt();
                            Transaction transaction = Transaction.getTransaction(transaction_id);
                            if (transaction == null || transaction.getCustomerAccount_no() != company.getAccount_no()) {
                                System.out.println("There is no such a transaction");
                                break;
                            }
                            SQL_Functions.return_things(transaction_id);
                            break;
                        case 3:
                            if (company.getBalance() >= company.getDebt())
                                SQL_Functions.pay_debt(company.getAccount_no());
                            else
                                System.out.println("Your balance is not enough");
                            break;
                        case 4:
                            SQL_Functions.other_questions2b(company.getAccount_no());
                            break;
                        case 5:
                            CCC.unregister_User(company.getAccount_no());
                            return;
                        case 6:
                            return;
                        default:
                            System.out.println("Choose a valid number, type it and press enter please");
                            break;
                    }
                }
            } else if (dealer != null) {
                while(true) {
                    System.out.println("Press a number and enter to choose:");
                    System.out.println("Actions:");
                    System.out.println(" 1. Show customer with more than some purchases\n 2. Show Dealer of the month\n 3. Make payment\n 4. Show Transactions\n  5. Unregister\n 6. Exit");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Purchases base: ");
                            int base = scanner.nextInt();
                            while (base < 0) {
                                System.out.println("Not a valid purchases base, please give a non-negative base");
                                System.out.println("Purchases base: ");
                                base = scanner.nextInt();
                            }
                            SQL_Functions.other_questions3(dealer.getAccount_no(), base);
                            break;
                        case 2:
                            CCC.dealer_of_the_month();
                            break;
                        case 3:
                            if (dealer.getEarnings() >= dealer.getDebt())
                                SQL_Functions.pay_debt(dealer.getAccount_no());
                            else
                                System.out.println("Your balance is not enough");
                            break;
                        case 4:
                            SQL_Functions.other_questions2b(dealer.getAccount_no());
                            break;
                        case 5:
                            CCC.unregister_User(dealer.getAccount_no());
                            return;
                        case 6:
                            return;
                        default:
                            System.out.println("Choose a valid number, type it and press enter please");
                            break;
                    }
                }
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
                    earnings = scanner.nextDouble();
                    while (earnings < 0) {
                        System.out.println("Give non-negative earnings please");
                        System.out.println("Earnings: ");
                        earnings = scanner.nextDouble();
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
