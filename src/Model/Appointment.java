package Model;

public class Appointment {
    private String custId;
    private String custName;
    private String appointment;
    private String time;
    private String date;

    public Appointment(String string, String rstString, String s, String string1) {
    }

    public Appointment(String custId, String custName, String appointment, String time, String date) {
        this.setCustId(custId);
        this.setCustName(custName);
        this.setAppointment(appointment);
        this.setTime(time);
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

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "custId='" + custId + '\'' +
                ", custName='" + custName + '\'' +
                ", appointment='" + appointment + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
