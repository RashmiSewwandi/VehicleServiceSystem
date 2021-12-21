package controller;

import Model.AddToService;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceController implements AddService{

    public static List<AddToService> searchVehicleNumber(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM AddToService WHERE vehicleNumber LIKE '%" + value + "%'");
        ResultSet rs = pstm.executeQuery();

        List<AddToService> addToServices = new ArrayList<>();

        while (rs.next()) {
            addToServices.add(new AddToService(
                    rs.getString("SId"),
                    rs.getString("vehicleNumber"),
                    rs.getString("vehicleModel"),
                    rs.getString("fualType"),
                    rs.getString("serviceType")
            ));
        }

        return addToServices;

    }

    @Override
    public boolean saveService(AddToService c) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO addToService VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,c.getSId());
        stm.setObject(2,c.getVehicleNumber());
        stm.setObject(3,c.getVehicleModel());
        stm.setObject(4,c.getFualType());
        stm.setObject(5,c.getServiceType());

        return stm.executeUpdate()>0;
    }


    @Override
    public boolean deleteService(String SId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM addToService WHERE SId='"+SId+"'")
                .executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<AddToService> getAllServic() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM addToService");
        ResultSet rst = stm.executeQuery();
        ArrayList<AddToService> services = new ArrayList<>();
        while (rst.next()) {
            services.add(new AddToService(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)


            ));
        }
        return services;

    }

    @Override
    public AddToService searchService(String vehicleNumber) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("select * from addToService where vehicleNumber=?");
        stm.setObject(1, vehicleNumber);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new AddToService(
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


}
