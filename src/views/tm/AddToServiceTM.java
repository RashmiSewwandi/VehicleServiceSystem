package views.tm;

public class AddToServiceTM {
    private String SId;
    private String vehicleNumber;
    private String vehicleModel;
    private String fualType;
    private String serviceType;

    public AddToServiceTM() {
    }

    public AddToServiceTM(String SId, String vehicleNumber, String vehicleModel, String fualType, String serviceType) {
        this.setSId(SId);
        this.setVehicleNumber(vehicleNumber);
        this.setVehicleModel(vehicleModel);
        this.setFualType(fualType);
        this.setServiceType(serviceType);
    }

    public String getSId() {
        return SId;
    }

    public void setSId(String SId) {
        this.SId = SId;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return "AddToServiceTM{" +
                "SId='" + SId + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", fualType='" + fualType + '\'' +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }
}
