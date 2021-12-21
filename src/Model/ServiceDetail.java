package Model;

public class ServiceDetail {
    private String vehicleNumber;
    private String serviceType;
    private String itemReplace;
    private String serviceCharge;

    public ServiceDetail() {
    }

    public ServiceDetail(String vehicleNumber, String serviceType, String itemReplace, String serviceCharge) {
        this.setVehicleNumber(vehicleNumber);
        this.setServiceType(serviceType);
        this.setItemReplace(itemReplace);
        this.setServiceCharge(serviceCharge);
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getItemReplace() {
        return itemReplace;
    }

    public void setItemReplace(String itemReplace) {
        this.itemReplace = itemReplace;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    @Override
    public String toString() {
        return "ServiceDetail{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", itemReplace='" + itemReplace + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                '}';
    }
}
