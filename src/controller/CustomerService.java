package controller;


import Model.Customer;
import views.tm.CustomerTM;
import java.sql.SQLException;
import java.util.ArrayList;


public interface CustomerService {
    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String custId) throws SQLException, ClassNotFoundException;

    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;

    Customer searchCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerTM customer) throws SQLException, ClassNotFoundException;


}


