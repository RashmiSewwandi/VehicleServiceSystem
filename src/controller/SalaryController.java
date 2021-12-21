package controller;



import Model.EmployeeSalary;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import views.tm.SalaryTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryController implements  SalaryService{
    public static List<EmployeeSalary> searchNIC(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Empsalary WHERE empId LIKE '%" + value + "%'");
        ResultSet rs = pstm.executeQuery();

        List<EmployeeSalary> employeeSalaries = new ArrayList<>();

        while (rs.next()) {
            employeeSalaries.add(new EmployeeSalary(
                    rs.getString("SID"),
                    rs.getString("empId"),
                    rs.getString("empName"),
                    rs.getString("tpNumber"),
                    rs.getDouble("salary"),
                    rs.getString("date")
            ));
        }

        return employeeSalaries;
    }

    @Override
    public boolean saveSalary(EmployeeSalary salary) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Empsalary VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,salary.getSID());
        stm.setObject(2,salary.getEmpId());
        stm.setObject(3,salary.getEmpName());
        stm.setObject(4,salary.getTpNumber());
        stm.setObject(5,salary.getSalary());
        stm.setObject(6,salary.getDate());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteSalary(String SID) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Empsalary WHERE SID='"+SID+"'")
                .executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }



    public ObservableList<SalaryTM> getSalaryList() throws SQLException, ClassNotFoundException {
        ObservableList <SalaryTM> salary = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        String query = "SELECT * FROM Empsalary";
        PreparedStatement pstm = con.prepareStatement(query);
        ResultSet set = pstm.executeQuery();
        while (set.next()){
            salary.add(new SalaryTM(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getDouble(5),
                    set.getString(6)


            ));
        }
        return salary;
    }



}
