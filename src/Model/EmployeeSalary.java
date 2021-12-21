package Model;

public class EmployeeSalary {
      private String SID;
      private String empId;
      private String empName;
      private String tpNumber;
      private double salary;
      private String date;

    public EmployeeSalary(){
    }

    public EmployeeSalary(String SID, String empId, String empName, String tpNumber, double salary, String date) {
        this.setSID(SID);
        this.setEmpId(empId);
        this.setEmpName(empName);
        this.setTpNumber(tpNumber);
        this.setSalary(salary);
        this.setDate(date);
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
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

    public String getTpNumber() {
        return tpNumber;
    }

    public void setTpNumber(String tpNumber) {
        this.tpNumber = tpNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "SID='" + SID + '\'' +
                ", empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", tpNumber='" + tpNumber + '\'' +
                ", salary=" + salary +
                ", date='" + date + '\'' +
                '}';
    }
}
