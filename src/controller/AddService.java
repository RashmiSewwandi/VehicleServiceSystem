package controller;

import Model.AddToService;



import java.sql.SQLException;
import java.util.ArrayList;


public interface AddService {
    public boolean saveService(AddToService c) throws SQLException, ClassNotFoundException;

    public boolean deleteService(String SId) throws SQLException, ClassNotFoundException;

    public ArrayList<AddToService> getAllServic() throws SQLException, ClassNotFoundException;

    AddToService searchService(String vehicleNumber) throws SQLException, ClassNotFoundException;

}
