package views.tm;

public class CartTM {
    private String vehicleNumber;
    private String vehicleModel;
    private String serviceType;
    private String itemReplace;
    private String serviceCharge;

    public CartTM() {
    }

    public CartTM(String vehicleNumber, String vehicleModel, String serviceType, String itemReplace, String serviceCharge) {
        this.setVehicleNumber(vehicleNumber);
        this.setVehicleModel(vehicleModel);
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

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
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
        return "CartTM{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", itemReplace='" + itemReplace + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                '}';
    }
}
