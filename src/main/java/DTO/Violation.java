package DTO;

public class Violation {
    private String violationID;
    private String name;
    private Double price;

    public Violation(String violationID, String name, Double price) {
        this.violationID = violationID;
        this.name = name;
        this.price = price;
    }

    public Violation() {
    }

    public String getViolationID() {
        return violationID;
    }

    public void setViolationID(String violationID) {
        this.violationID = violationID;
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

    @Override
    public String toString() {
        return "Violation{" +
                "violationID='" + violationID + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
