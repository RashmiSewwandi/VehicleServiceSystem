package Model;

public class Vehicle {
    private String appointment;
    private String custName;
    private String vehicleNumber;
    private String vehicleModel;
    private String fualType;
    private String date;

    public Vehicle() {
    }

    public Vehicle(String appointment, String custName, String vehicleNumber, String vehicleModel, String fualType, String date) {
        this.setAppointment(appointment);
        this.setCustName(custName);
        this.setVehicleNumber(vehicleNumber);
        this.setVehicleModel(vehicleModel);
        this.setFualType(fualType);
        this.setDate(date);
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
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

    public String getFualType() {
        return fualType;
    }

    public void setFualType(String fualType) {
        this.fualType = fualType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "appointment='" + appointment + '\'' +
                ", custName='" + custName + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", fualType='" + fualType + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
