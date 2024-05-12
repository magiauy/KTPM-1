package DTO;

import java.time.LocalDate;

public class ViolatioUsage {
    private String id;
    private String name;
    private Double price;
    private LocalDate date;
    private String note;

    public ViolatioUsage(String id, String name, Double price, LocalDate date, String note) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ViolatioUsage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
