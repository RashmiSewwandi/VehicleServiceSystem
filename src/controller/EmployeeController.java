package controller;

import Model.Employee;
import db.DbConnection;
import views.tm.EmployeeTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController implements EmployeeService{


    public static List<Employee> searchNIC(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM employee WHERE empId LIKE '%" + value + "%'");
        ResultSet rs = pstm.executeQuery();

        List<Employee> employees = new ArrayList<>();

        while (rs.next()) {
            employees.add(new Employee(
                    rs.getString("empId"),
                    rs.getString("empName"),
                    rs.getString("empAddress"),
                    rs.getString("tpNumber"),
                    rs.getString("email")
            ));
        }

        return employees;

    }


    @Override
    public boolean saveEmployee(Employee e) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO employee VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,e.getEmpId());
        stm.setObject(2,e.getEmpName());
        stm.setObject(3,e.getEmpAddress());
        stm.setObject(4,e.getTpNumber());
        stm.setObject(5,e.getEmail());

        return stm.executeUpdate()>0;
    }


    @Override
    public boolean deleteEmployee(String empId) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM employee WHERE empId='"+empId+"'")
                .executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public ArrayList<Employee> getAllEmployee() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM employee");
        ResultSet rst = stm.executeQuery();
        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()) {
            employees.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)


            ));
        }
        return employees;
    }


    @Override
    public Employee searchEmployee(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("select * from employee where empId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Employee(
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


    @Override
    public boolean updateEmployee(EmployeeTM employee) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE employee SET empName=?,empAddress=?, tpNumber=?,email=?  WHERE empId=?");
        stm.setObject(1,employee.getEmpName());
        stm.setObject(2,employee.getEmpAddress());
        stm.setObject(3,employee.getTpNumber());
        stm.setObject(4,employee.getEmail());
        stm.setObject(5,employee.getEmpId());


        return stm.executeUpdate()>0;
    }


}
