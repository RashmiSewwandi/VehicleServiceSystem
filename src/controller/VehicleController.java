package controller;

import Model.Vehicle;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import views.tm.VehicleTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VehicleController implements VehicleService{

    public static List<Vehicle> SearchName(String value) throws SQLException, ClassNotFoundException {
            Connection con = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM vehicle WHERE custName LIKE '%" + value + "%'");
            ResultSet rs = pstm.executeQuery();

            List<Vehicle> vehicles = new ArrayList<>();

            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getString("appointment"),
                        rs.getString("custName"),
                        rs.getString("vehicleNumber"),
                        rs.getString("vehicleModel"),
                        rs.getString("fualType"),
                        rs.getString("date")
                ));
            }

            return vehicles;

    }

    //save vehicle
    public boolean saveVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String quary ="INSERT INTO vehicle VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = con.prepareStatement(quary);

        pstm.setObject(1,vehicle.getAppointment());
        pstm.setObject(2,vehicle.getCustName());
        pstm.setObject(3,vehicle.getVehicleNumber());
        pstm.setObject(4,vehicle.getVehicleModel());
        pstm.setObject(5,vehicle.getFualType());
        pstm.setObject(6,vehicle.getDate());

        return pstm.executeUpdate()>0;
    }


    //delete vehicle
    public boolean deleteVehicle(String vehicleNumber) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection()
                .prepareStatement("DELETE FROM vehicle WHERE vehicleNumber= '"+vehicleNumber+"'").executeUpdate()>0){
            return true;

        }else {
            return false;
        }
    }

    //table
    public ObservableList<VehicleTM> getVehicleList() throws SQLException, ClassNotFoundException {
        ObservableList <VehicleTM> vehicle = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM vehicle";
        PreparedStatement pstm = con.prepareStatement(query);
        ResultSet set = pstm.executeQuery();
        while (set.next()){
            vehicle.add(new VehicleTM(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    set.getString(6)


            ));
        }
        return vehicle;
    }



    @Override
    public Vehicle searchVehicle(String vehicleNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("select * from vehicle where vehicleNumber=?");
        stm.setObject(1, vehicleNumber);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

        } else {
            return null;
        }
    }


}
