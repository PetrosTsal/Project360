package backend;

public class User {
    // attributes
    private String username;
    private String password;
    private String name;
    private int account_no; // primary key
    private float debt;
    // ...
    public  User(){

    }
    public User(String username , String password ,String name, int account_no, float debt) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.account_no = account_no;
        this.debt = debt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccount_no() {
        return account_no;
    }

    public void setAccount_no(int account_no) {
        this.account_no = account_no;
    }

    public float getDebt() {
        return debt;
    }

    public void setDebt(float debt) {
        this.debt = debt;
    }
// functions
}
