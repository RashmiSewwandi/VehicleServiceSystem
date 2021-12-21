package Model;

import java.util.ArrayList;

public class Payment {
    private String paymentId;
    private String NIC;
    private String paymentDate;
    private String paymentTime;
    private ArrayList<ServiceDetail> service;

    public Payment() {
    }

    public Payment(String paymentId, String NIC, String paymentDate, String paymentTime, ArrayList<ServiceDetail> service) {
        this.setPaymentId(paymentId);
        this.setNIC(NIC);
        this.setPaymentDate(paymentDate);
        this.setPaymentTime(paymentTime);
        this.setService(service);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public ArrayList<ServiceDetail> getService() {
        return service;
    }

    public void setService(ArrayList<ServiceDetail> service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", NIC='" + NIC + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", service=" + service +
                '}';
    }
}
