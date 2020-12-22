package hcmus.edu.project02;

public class Account {
    private String user;
    private String password;

    public Account(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Account))
            return false;

        Account account = (Account) obj;

        return this.user.equals(account.getUser())
                && this.password.equals(account.getPassword());
    }

    public String getUser() { return this.user; }
    public String getPassword() {return  this.password; }
}
