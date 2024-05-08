package DTO;

/**
 *
 * @author NGOC
 */
public class Service {
    private String serviceID;
    private String name;
    private Double pricePerUnit;
    private String unit;
    private String type;
    public Service(String serviceID, String name, Double pricePerUnit, String unit, String type) {
        this.serviceID = serviceID;
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.type = type;
    }

    public Service() {
    }
    public Service(Service service) {
        this.serviceID = service.serviceID;
        this.name = service.name;
        this.pricePerUnit = service.pricePerUnit;
        this.unit = service.unit;
        this.type = service.type;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", name='" + name + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", unit='" + unit + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
