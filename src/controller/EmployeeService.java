package controller;



import Model.Employee;
import views.tm.EmployeeTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeService {

    public boolean saveEmployee(Employee e) throws SQLException, ClassNotFoundException;

    public boolean deleteEmployee(String empId) throws SQLException, ClassNotFoundException;

    public ArrayList<Employee> getAllEmployee() throws SQLException, ClassNotFoundException;

    Employee searchEmployee(String id) throws SQLException, ClassNotFoundException;

    boolean updateEmployee(EmployeeTM employee) throws SQLException, ClassNotFoundException;


}
