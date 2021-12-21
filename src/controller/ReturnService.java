package controller;


import Model.VehicleReturn;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnService {
    public boolean saveReturn(VehicleReturn v) throws SQLException, ClassNotFoundException;

    public boolean deleteReturn(String vehicleNumber) throws SQLException, ClassNotFoundException;

    public ArrayList<VehicleReturn> getAllReturn() throws SQLException, ClassNotFoundException;
}
