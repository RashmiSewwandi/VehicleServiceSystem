package controller;


import Model.Employee;
import Model.EmployeeSalary;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import util.ValidationUtil;
import views.tm.SalaryTM;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class SalaryFormController implements Initializable {
    public JFXTextField txtNIC;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtTp;
    public JFXTextField txtSalary;

    public TableView <SalaryTM>tblSalary;
    public TableColumn colsId;
    public TableColumn colNIC;
    public TableColumn colTP;
    public TableColumn colSalary;
    public TableColumn colDate;
    public TableColumn colName;

    public TextField txtSearch;
    public Label lblDate;
    public JFXTextField txtSID;
    public JFXButton btnAddSalary;


    public void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    public void initialize(URL location, ResourceBundle resources)  {

        storeValidations();
        btnAddSalary.setDisable(true);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });


        try {
            AddSalaryToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadDate();

    }

    public void AddSalaryToTable() throws SQLException, ClassNotFoundException {

        ObservableList<SalaryTM> salary = new SalaryController().getSalaryList();

        colsId.setStyle("-fx-alignment: CENTER;");
        colNIC.setStyle("-fx-alignment: CENTER;");
        colTP.setStyle("-fx-alignment: CENTER;");
        colSalary.setStyle("-fx-alignment: CENTER;");
        tblSalary.setStyle("-fx-background-color: blue;");

        colsId.setCellValueFactory(new PropertyValueFactory<>("SID"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colTP.setCellValueFactory(new PropertyValueFactory<>("tpNumber"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblSalary.setItems(salary);
    }


    ObservableList<SalaryTM> obList = FXCollections.observableArrayList();
    public void Add_Salary_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        EmployeeSalary salary = new EmployeeSalary(
                txtSID.getText(),
                txtNIC.getText(),
                txtName.getText(),
                txtTp.getText(),
                Double.parseDouble(txtSalary.getText()),
                lblDate.getText()
        );

        if(new SalaryController().saveSalary(salary)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            AddSalaryToTable();
            clearField();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }


    public void Delete_Salary_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SalaryTM salary = tblSalary.getSelectionModel().getSelectedItem();
        String sId =salary.getSID();

        if (new SalaryController().deleteSalary(sId)){
            new Alert(Alert.AlertType.INFORMATION,"Delete Complete").show();
           AddSalaryToTable();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
        clearField();
    }

    private void clearField(){
        txtSID.clear();
        txtNIC.clear();
        txtName.clear();
        txtAddress.clear();
        txtTp.clear();
        txtSalary.clear();
        lblDate.requestFocus();
    }

    public void Search_Employee_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String empId = txtNIC.getText();

        Employee employee = new EmployeeController().searchEmployee(empId);
        if (employee == null) {
            new Alert(Alert.AlertType.WARNING,"Empty");
        }else{
            setData(employee);
        }
    }

    private void setData(Employee employee) {
        txtNIC.setText(employee.getEmpId());
        txtName.setText(employee.getEmpName());
        txtAddress.setText(employee.getEmpAddress());
        txtTp.setText(employee.getTpNumber());
    }

    public void moveToNIC(ActionEvent actionEvent) {
        txtNIC.requestFocus();
    }

    public void Search_Employee(ActionEvent actionEvent) {
        search(txtNIC.getText());
    }

    private void search(String value) {
        try {
            List<EmployeeSalary> employeeSalaries = SalaryController.searchNIC(value);
            ObservableList<SalaryTM> tableData = FXCollections.observableArrayList();
            for (EmployeeSalary employeeSalary : employeeSalaries) {
                tableData.add(new SalaryTM(
                        employeeSalary.getSID(),
                        employeeSalary.getEmpId(),
                        employeeSalary.getEmpName(),
                        employeeSalary.getTpNumber(),
                        employeeSalary.getSalary(),
                        employeeSalary.getDate()
                ));
            }
            tblSalary.getItems().setAll(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddSalary);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success ..").showAndWait();
            }
        }
    }

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern SIDPattern = Pattern.compile("^[S|s][-][0-9]{3,}$");
    Pattern NICPattern = Pattern.compile("^([0-9]{9}[|v|V]|[0-9]{12})$");


    private void storeValidations() {
        map.put(txtSID, SIDPattern);
        map.put(txtNIC, NICPattern);


    }

}
