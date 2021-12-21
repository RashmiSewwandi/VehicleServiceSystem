package controller;

import Model.Appointment;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import views.tm.AppointmentTM;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController implements AppointmentService{

    public static List<Appointment> SearchAPI(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Appointment WHERE appointment LIKE '%" + value + "%'");
        ResultSet rs = pstm.executeQuery();

        List<Appointment> appointments = new ArrayList<>();

        while (rs.next()) {
            appointments.add(new Appointment(
                    rs.getString("custId"),
                    rs.getString("custName"),
                    rs.getString("appointment"),
                    rs.getString("time"),
                    rs.getString("date")
            ));
        }

        return appointments;

    }

    public String getAppointmentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT appointment FROM Appointment ORDER BY appointment DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId +1;
            if (tempId < 9) {
                return "A-00" + tempId;
            } else if (tempId < 99) {
                return "A-0" + tempId;
            } else {
                return "A-" + tempId;
            }

        } else {
            return "A-001";
        }
    }



    public boolean saveAppointment(Appointment appointment) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Appointment VALUES (?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, appointment.getCustId());
        stm.setObject(2, appointment.getCustName());
        stm.setObject(3, appointment.getAppointment());
        stm.setObject(4, appointment.getTime());
        stm.setObject(5, appointment.getDate());

        return stm.executeUpdate()>0;
    }

    @Override
    public Appointment searchAppointment(String appointment) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("select * from Appointment where appointment=?");
        stm.setObject(1, appointment);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Appointment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }



    public ObservableList<AppointmentTM> getAppointmentList() throws SQLException, ClassNotFoundException {
        ObservableList <AppointmentTM> appointment = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Appointment";
        PreparedStatement pstm = con.prepareStatement(query);
        ResultSet set = pstm.executeQuery();
        while (set.next()){
            appointment.add(new AppointmentTM(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5)

            ));
        }
        return appointment;
    }


    public boolean deleteCustomer(String appointmentNumber) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection()
                .prepareStatement("DELETE FROM Appointment WHERE appointment= '"+appointmentNumber+"'").executeUpdate()>0){
            return true;

        }else {
            return false;
        }
    }

}
