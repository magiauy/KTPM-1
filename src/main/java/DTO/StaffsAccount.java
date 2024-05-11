package DTO;

public class StaffsAccount {
    private String username;
    private String password;
    private String id;

    public StaffsAccount(String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }
    public StaffsAccount(){}

    public StaffsAccount(StaffsAccount staffsAccount){
        this.username = staffsAccount.username;
        this.password = staffsAccount.password;
        this.id = staffsAccount.id;
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
        return "StaffsAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
