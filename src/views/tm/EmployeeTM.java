package views.tm;

public class EmployeeTM {
    private String empId;
    private String empName;
    private String empAddress;
    private String tpNumber;
    private String email;


    public EmployeeTM() {
    }

    public EmployeeTM(String empId, String empName, String empAddress, String tpNumber, String email) {
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setEmpAddress(empAddress);
        this.setTpNumber(tpNumber);
        this.setEmail(email);
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getTpNumber() {
        return tpNumber;
    }

    public void setTpNumber(String tpNumber) {
        this.tpNumber = tpNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmployeeTM{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", tpNumber='" + tpNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
