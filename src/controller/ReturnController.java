package controller;


import Model.VehicleReturn;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnController implements ReturnService {

    @Override
    public boolean saveReturn(VehicleReturn v) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO vehicleReturn VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,v.getNIC());
        stm.setObject(2,v.getCustName());
        stm.setObject(3,v.getVehicleNumber());
        stm.setObject(4,v.getVehicleModel());
        stm.setObject(5,v.getDescription());
        stm.setObject(6,v.getNextDate());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteReturn(String vehicleNumber) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM vehicleReturn WHERE vehicleNumber='"+vehicleNumber+"'")
                .executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<VehicleReturn> getAllReturn() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM vehicleReturn");
        ResultSet rst = stm.executeQuery();
        ArrayList<VehicleReturn> vehicleReturns = new ArrayList<>();
        while (rst.next()) {
            vehicleReturns.add(new VehicleReturn(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)

            ));
        }
        return vehicleReturns;
    }
}
