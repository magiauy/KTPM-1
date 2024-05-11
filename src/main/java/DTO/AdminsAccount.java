package DTO;

public class AdminsAccount {
    private String username;
    private String password;
    private String id;

    public AdminsAccount(String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }
    public AdminsAccount() {}
    public AdminsAccount(AdminsAccount adminsAccount) {
        this.username = adminsAccount.username;
        this.password = adminsAccount.password;
        this.id = adminsAccount.id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdminsAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
