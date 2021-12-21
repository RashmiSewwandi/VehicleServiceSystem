package Model;

import java.time.LocalDate;

public class VehicleReturn {
    private  String NIC;
    private String custName;
    private String vehicleNumber;
    private String vehicleModel;
    private String description;
    private String nextDate;

    public VehicleReturn(String text, String txtCustomerNameText, String txtVehicleNumberText, String txtVehicleModelText, String txtDescriptionText, LocalDate value) {
    }

    public VehicleReturn(String NIC, String custName, String vehicleNumber, String vehicleModel, String description, String nextDate) {
        this.setNIC(NIC);
        this.setCustName(custName);
        this.setVehicleNumber(vehicleNumber);
        this.setVehicleModel(vehicleModel);
        this.setDescription(description);
        this.setNextDate(nextDate);
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    @Override
    public String toString() {
        return "VehicleReturn{" +
                "NIC='" + NIC + '\'' +
                ", custName='" + custName + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", description='" + description + '\'' +
                ", nextDate='" + nextDate + '\'' +
                '}';
    }
}
