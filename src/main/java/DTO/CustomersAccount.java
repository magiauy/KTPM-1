package DTO;

public class CustomersAccount {
    private String username;
    private String password;
    private String id;

    public CustomersAccount(String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }
    public CustomersAccount(){}
    public CustomersAccount(CustomersAccount customersAccount){
        this.username = customersAccount.username;
        this.password = customersAccount.password;
        this.id = customersAccount.id;
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
        return "CustomersAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
