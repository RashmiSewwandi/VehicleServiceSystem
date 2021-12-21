package controller;


import Model.EmployeeSalary;
import java.sql.SQLException;


public interface SalaryService {
    public boolean saveSalary(EmployeeSalary salary) throws SQLException, ClassNotFoundException;

    public boolean deleteSalary(String sId) throws SQLException, ClassNotFoundException;

}
