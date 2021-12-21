package controller;


import Model.Customer;
import db.DbConnection;
import views.tm.CustomerTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService{

    //search NIC
    public static List<Customer> searchNIC(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer WHERE custId LIKE '%" + value + "%'");
        ResultSet rs = pstm.executeQuery();

        List<Customer> customers = new ArrayList<>();

        while (rs.next()) {
            customers.add(new Customer(
                    rs.getString("custId"),
                    rs.getString("custName"),
                    rs.getString("custAddress"),
                    rs.getString("contactNumber"),
                    rs.getString("email"),
                    rs.getString("date")
            ));
        }

        return customers;

    }

    @Override
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO customer VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);

        stm.setObject(1,c.getCustId());
        stm.setObject(2,c.getCustName());
        stm.setObject(3,c.getCustAddress());
        stm.setObject(4,c.getContactNumber());
        stm.setObject(5,c.getEmail());
        stm.setObject(6,c.getDate());

        return stm.executeUpdate()>0;

    }

    @Override
    public boolean deleteCustomer(String custId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE custId='"+custId+"'")
                .executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }



    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer");
        ResultSet rst = stm.executeQuery();

        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return customers;
    }


    @Override
    public Customer searchCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("select * from customer where custId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(
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


    @Override
    public boolean updateCustomer(CustomerTM customer) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET custName=?,custAddress=?, contactNumber=?,email=?,date=? WHERE custId=?");
        stm.setObject(1,customer.getCustName());
        stm.setObject(2,customer.getCustAddress());
        stm.setObject(3,customer.getContactNumber());
        stm.setObject(4,customer.getEmail());
        stm.setObject(5,customer.getDate());
        stm.setObject(6,customer.getCustId());

        return stm.executeUpdate()>0;
    }


}
