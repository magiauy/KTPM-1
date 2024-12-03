package DTO;

import java.time.LocalDate;

public class Table_User {
    private String t;
    private String la;
    private LocalDate de;
    private String active;
    public Table_User() {}

    public Table_User(String t, String la, LocalDate de, String active) {
        this.t = t;
        this.la = la;
        this.de = de;
        this.active = active;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    public LocalDate getDe() {
        return de;
    }

    public void setDe(LocalDate de) {
        this.de = de;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
