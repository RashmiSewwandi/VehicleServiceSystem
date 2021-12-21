package controller;

import Model.Appointment;
import java.sql.SQLException;


public interface AppointmentService {

    public boolean saveAppointment(Appointment appointment) throws SQLException, ClassNotFoundException;
    Appointment searchAppointment(String appointment) throws SQLException, ClassNotFoundException;
}
