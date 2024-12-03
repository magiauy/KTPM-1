package DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;

/**
 *
 * @author NGOC
 */
public class Service {
    @NotNull(message = "Service ID is required")
    @NotEmpty(message = "Service ID is required")
    private String serviceID;
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "Price per unit is required")
    @Positive(message = "Price per unit must be positive")
    private Double pricePerUnit;
    @NotNull(message = "Unit is required")
    @NotEmpty(message = "Unit is required")
    private String unit;
    @NotNull(message = "Type is required")
    @NotEmpty(message = "Type is required")
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
