package controller;


import Model.Vehicle;
import java.sql.SQLException;

public interface VehicleService {
    public boolean saveVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException;
    Vehicle searchVehicle(String vehicleNumber) throws SQLException, ClassNotFoundException;

}
