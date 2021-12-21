package controller;


import Model.Employee;
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
import javafx.scene.input.MouseEvent;
import util.ValidationUtil;
import views.tm.EmployeeTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddEmployeeFormController implements Initializable {
    public JFXTextField txtNIC;
    public JFXTextField txtEpName;
    public JFXTextField txtEpAddress;
    public JFXTextField txtTpNumber;
    public JFXTextField txtEmail;

    public TableView <EmployeeTM>tblEmployee;

    public TableColumn colNIC;
    public TableColumn colFullName;
    public TableColumn colAddress;
    public TableColumn colTpNumber;
    public TableColumn colMail;
    public TextField txtNICSearch;
    public JFXButton btnAddEmployee;


    public void initialize(URL location, ResourceBundle resources) {
        storeValidations();
        btnAddEmployee.setDisable(true);

        txtNICSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        colNIC.setStyle("-fx-alignment: CENTER;");
        colTpNumber.setStyle("-fx-alignment: CENTER;");
        tblEmployee.setStyle("-fx-background-color: blue;");

        colNIC.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colTpNumber.setCellValueFactory(new PropertyValueFactory<>("tpNumber"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            addEmployeetoTable(new EmployeeController().getAllEmployee());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void textFields_Key_Released(KeyEvent keyEvent) {
            Object response = ValidationUtil.validate(map,btnAddEmployee);
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
    Pattern NICPattern = Pattern.compile("^([0-9]{9}[|v|V]|[0-9]{12})$");
    Pattern FullNamePattern = Pattern.compile("^[A-z. ]{3,20}$");
    Pattern AddressPattern = Pattern.compile("^[A-z0-9/ ,]{6,30}[.]?$");
    Pattern TpNumberPattern = Pattern.compile("^([+]\\d{2})?\\d{10}$");
    Pattern EmailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");

    private void storeValidations() {
        map.put(txtNIC, NICPattern);
        map.put(txtEpName, FullNamePattern);
        map.put(txtEpAddress, AddressPattern);
        map.put(txtTpNumber, TpNumberPattern);
        map.put(txtEmail, EmailPattern);
    }


    public void addEmployeetoTable(ArrayList<Employee> employees)  {
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
        employees.forEach(e->{
            obList.add(
                    new EmployeeTM(e.getEmpId(),e.getEmpName(),e.getEmpAddress(),e.getTpNumber(),e.getEmail()));
        });
        tblEmployee.setItems(obList);

    }



    public void Add_Employee_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee e1 = new Employee(
                txtNIC.getText(),
                txtEpName.getText(),
                txtEpAddress.getText(),
                txtTpNumber.getText(),
                txtEmail.getText()
        );

        if(new EmployeeController().saveEmployee(e1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            addEmployeetoTable(new EmployeeController().getAllEmployee());
            clearField();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }


    public void Update_Employee_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        EmployeeTM employee = new EmployeeTM(
                txtNIC.getText(),
                txtEpName.getText(),
                txtEpAddress.getText(),
                txtTpNumber.getText(),
                txtEmail.getText()
        );
        if(new EmployeeController().updateEmployee(employee)){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            addEmployeetoTable(new EmployeeController().getAllEmployee());
            clearField();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }


    public void Delete_Employee_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        EmployeeTM employee = tblEmployee.getSelectionModel().getSelectedItem();
        String empId =employee.getEmpId();

        if (new EmployeeController().deleteEmployee(empId)){
            new Alert(Alert.AlertType.INFORMATION,"Delete Complete").show();
            addEmployeetoTable(new EmployeeController().getAllEmployee());
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
        clearField();
    }

    private void clearField(){
        txtNIC.clear();
        txtEpName.clear();
        txtEpAddress.clear();
        txtTpNumber.clear();
        txtEmail.clear();
        txtNIC.requestFocus();
    }


    public void Search_Employee(ActionEvent actionEvent) {
        search(txtNICSearch.getText());
    }

    private void search(String value) {
        try {
            List<Employee> employees = EmployeeController.searchNIC(value);
            ObservableList<EmployeeTM> tableData = FXCollections.observableArrayList();
            for (Employee employee : employees) {
                tableData.add(new EmployeeTM(
                        employee.getEmpId(),
                        employee.getEmpName(),
                        employee.getEmpAddress(),
                        employee.getTpNumber(),
                        employee.getEmail()
                ));
            }
            tblEmployee.getItems().setAll(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void moveToName(ActionEvent actionEvent) {
        txtEpName.requestFocus();
    }

    public void moveToAddress(ActionEvent actionEvent) {
        txtEpAddress.requestFocus();
    }

    public void moveToTpNumber(ActionEvent actionEvent) {
        txtTpNumber.requestFocus();
    }

    public void MoveToEmail(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }


    public void table_Click_OnAction(MouseEvent mouseEvent) {
        EmployeeTM employee = null;
        employee = tblEmployee.getSelectionModel().getSelectedItem();
        txtNIC.setText(employee.getEmpId());
        txtEpName.setText(employee.getEmpName());
        txtEpAddress.setText(employee.getEmpAddress());
        txtTpNumber.setText(employee.getTpNumber());
        txtEmail.setText(employee.getEmail());
    }


}
