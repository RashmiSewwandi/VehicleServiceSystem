package views.tm;

public class CustomerTM {
    private String custId;
    private String custName;
    private String custAddress;
    private String contactNumber;
    private String email;
    private String date;

    public CustomerTM() {
    }

    public CustomerTM( String custId, String custName, String custAddress, String contactNumber, String email, String date) {

        this.setCustId(custId);
        this.setCustName(custName);
        this.setCustAddress(custAddress);
        this.setContactNumber(contactNumber);
        this.setEmail(email);
        this.setDate(date);
    }




    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                ", custId='" + custId + '\'' +
                ", custName='" + custName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
