package DTO;

public class Acount {
    private int id;
    private String username;
    private String password;
    private String loai;

    public Acount() {
    }

    public Acount(int id, String username, String password, String loai) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLoai() {
        return loai;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    @Override
    public String toString() {
        return "Acount [id=" + id + ", username=" + username + ", password=" + password + ", loai=" + loai + "]";
    }
}
