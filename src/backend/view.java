package backend;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class view {

    public static void user_login() throws SQLException, ClassNotFoundException {
        String login ;
        String usernam , passwor ;
        Scanner myObj = new Scanner(System.in);
        System.out.println("Choose the category of user you belong to .");
        login = myObj.nextLine();
        switch (login) {
            case "Civilian":
                System.out.println("Give me please your username .");
                usernam = myObj.nextLine();
                System.out.println("Give me please and your password .");
                passwor = myObj.nextLine();
                Civilian civi = new Civilian() ;
                if ( civi.getCivilian(usernam,passwor) != null){
                    System.out.println("Congratulations" +usernam +"you have succesfully logged in to our CCC ");
                    break;
                }else {
                    System.out.println("Authentication failed . Please try again .");
                    user_login();
                    break;
                }
            case "Company":
                System.out.println("Give me please your username .");
                usernam = myObj.nextLine();
                System.out.println("Give me please and your password .");
                passwor = myObj.nextLine();
                Company comp = new Company() ;
                if ( comp.getCompany(usernam,passwor) != null){
                    System.out.println("Congratulations" +usernam +"you have succesfully logged in to our CCC ");
                    break;
                }else {
                    System.out.println("Authentication failed . Please try again .");
                    user_login();
                    break;
                }
            case "Dealer":
                System.out.println("Give me please your username .");
                usernam = myObj.nextLine();
                System.out.println("Give me please and your password .");
                passwor = myObj.nextLine();
                Dealer deal = new Dealer() ;
                if ( deal.getDealer(usernam,passwor) != null){
                    System.out.println("Congratulations" +usernam +"you have succesfully logged in to our CCC ");
                    break;
                }else {
                    System.out.println("Authentication failed . Please try again .");
                    user_login();
                    break;
                }
            default:
                System.out.println("The category you chose is not valid . Please try again. ");
                user_login();
                break;
        }
        return ;

    }

    public static void user_register() throws SQLException, ClassNotFoundException {
        String register ;
        String usernam , passwor , nam;
        int account_n , credit_limi ;
        float deb , balanc , commissio , earning;
        Date expiration_dat = new Date(Calendar.DATE);
        Scanner myObj = new Scanner(System.in);
        System.out.println("Choose the category you want to register as .");
        register  = myObj.nextLine();
        switch (register){
            case "Civilian":
                System.out.println("Give me all your personal infos please");
                System.out.println("Give me your username please");
                usernam = myObj.nextLine();
                System.out.println("Give me your password please");
                passwor = myObj.nextLine();
                System.out.println("Give me your name please");
                nam = myObj.nextLine();
                System.out.println("Give me your account_number please");
                account_n = myObj.nextInt();
                System.out.println("Give me your debt please");
                deb = myObj.nextFloat();
                System.out.println("Give me your card's expiration date please");
                //expiration_dat = myObj.nextLine();
                System.out.println("Give me your balance please");
                balanc = myObj.nextFloat() ;
                System.out.println("Give me your card's credit limit please");
                credit_limi = myObj.nextInt();
                Civilian civ = new Civilian();
                System.out.println(civ.register_Civilian(usernam,passwor,nam,account_n,deb,expiration_dat,balanc,credit_limi));
                break;
            case "Company":
                System.out.println("Give me all your personal infos please");
                System.out.println("Give me your username please");
                usernam = myObj.nextLine();
                System.out.println("Give me your password please");
                passwor = myObj.nextLine();
                System.out.println("Give me your name please");
                nam = myObj.nextLine();
                System.out.println("Give me your account_number please");
                account_n = myObj.nextInt();
                System.out.println("Give me your debt please");
                deb = myObj.nextFloat();
                System.out.println("Give me your card's expiration date please");
                //expiration_dat =(Date) myObj.nextLine();
                System.out.println("Give me your balance please");
                balanc = myObj.nextFloat() ;
                System.out.println("Give me your card's credit limit please");
                credit_limi = myObj.nextInt();
                Company comp = new Company();
                System.out.println(comp.register_Company(usernam,passwor,nam,account_n,deb,expiration_dat,balanc,credit_limi));
                break;
            case "Dealer":
                System.out.println("Give me all your personal infos please");
                System.out.println("Give me your username please");
                usernam = myObj.nextLine();
                System.out.println("Give me your password please");
                passwor = myObj.nextLine();
                System.out.println("Give me your name please");
                nam = myObj.nextLine();
                System.out.println("Give me your account_number please");
                account_n = myObj.nextInt();
                System.out.println("Give me your debt please");
                deb = myObj.nextFloat();
                System.out.println("Give me your commission please");
                commissio = myObj.nextFloat();
                System.out.println("Give me your earnings please");
                earning = myObj.nextFloat();
                Dealer deal = new Dealer() ;
                System.out.println(deal.register_Dealer(usernam,passwor,nam,account_n,deb,commissio,earning));
                break;
            default :
                System.out.println("The category you chose is not valid . Please try again. ");
                user_register();
                break;
        }
        return ;
    }

    public static void makeTransaction(int dealer_account_no, int customer_account_no, float amount) throws SQLException, ClassNotFoundException {

    }

    public static void initial_menu() throws SQLException, ClassNotFoundException {
        int choice = 0 ;
        String register ;
        Scanner myObj = new Scanner(System.in);
        while(choice == 0) {
            System.out.println("If you want to login to our CCC type login .If you are a new member type register.");
            register = myObj.nextLine();
            if (register.equals("login")) {
                user_login();
                choice = 1;
            } else if (register.equals("register")) {
                user_register();
                choice = 1;
            } else if (register.equals("purchase")) {
                int dealer_accountNo = myObj.nextInt();
                int customer_accountNo = myObj.nextInt();
                float amount = myObj.nextFloat();

                SQL_Functions.purchase(dealer_accountNo, customer_accountNo, amount);
            }
            else {
                System.out.println("Invalid input , please try again");
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Welcome to our CCC , if you want to exit from our program type  or start if you want to continue.");
        Scanner myObj = new Scanner(System.in);
        String start ;
        start = myObj.nextLine();
        while ( !start.equals("exit")) {
            initial_menu();
            start = myObj.nextLine();
        }
    }
}
