package controller;


import Model.Appointment;
import Model.Customer;
import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXTimePicker;
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
import views.tm.AppointmentTM;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class AppointmentFormController implements Initializable {

    public TableView<AppointmentTM> tblAppointment;
    public TableColumn colAppointment;
    public TableColumn colCID;
    public TableColumn colTime;
    public TableColumn colDate;
    public TableColumn colName;

    public Label lblDate;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContactNumber;
    public TextField txtCustomerID;
    public JFXButton txtCancel;
    public TextField txtMail;
    public Label lblAppNumber;
    public TextField txtSearch;
    public JFXTimePicker txtTime;
    public JFXButton btnAddAppointment;


    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }


    public void initialize(URL location, ResourceBundle resources)  {
        loadDateAndTime();
        try {
            AddAppointmentToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setAppointmentId();

        storeValidations();
        btnAddAppointment.setDisable(true);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }

    //show data to table
    public void AddAppointmentToTable() throws SQLException, ClassNotFoundException {
        ObservableList<AppointmentTM> appointment = new AppointmentController().getAppointmentList();

        colCID.setStyle("-fx-alignment: CENTER;");
        colAppointment.setStyle("-fx-alignment: CENTER;");
        colDate.setStyle("-fx-alignment: CENTER;");
        colTime.setStyle("-fx-alignment: CENTER;");
        tblAppointment.setStyle("-fx-background-color: blue;");


        colCID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colAppointment.setCellValueFactory(new PropertyValueFactory<>("appointment"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblAppointment.setItems(appointment);

    }


    //btn Add appointment to table
    ObservableList<AppointmentTM> obList = FXCollections.observableArrayList();
    public void addAppointmentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Appointment appointment = new Appointment(
                txtCustomerID.getText(),
                txtName.getText(),
                lblAppNumber.getText(),
                txtTime.getValue().toString(),
                lblDate.getText()
        );
        if(new AppointmentController().saveAppointment(appointment)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved Appointment....");
            AddAppointmentToTable();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again....");
        }
    }


    //btn delete
    public void Cansel_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        AppointmentTM appointment = tblAppointment.getSelectionModel().getSelectedItem();
        String appointmentNumber =appointment.getAppointment();
        if (new AppointmentController().deleteCustomer(appointmentNumber)){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Success..").show();
            AddAppointmentToTable();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }
    }



    public void Customer_Search_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtCustomerID.getText();
        Customer customer = new CustomerController().searchCustomer(customerId);
        if (customer == null) {
            new Alert(Alert.AlertType.WARNING,"Try again");
        }else{
            setData(customer);
        }
    }



    void setData(Customer customer) {
        txtCustomerID.setText(customer.getCustId());
        txtName.setText(customer.getCustName());
        txtAddress.setText(customer.getCustAddress());
        txtContactNumber.setText(customer.getContactNumber());
        txtMail.setText(customer.getEmail());
    }



    private void setAppointmentId() {
        try {
            lblAppNumber.setText(new AppointmentController().getAppointmentId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void Search_Customer(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<Appointment> appointments = AppointmentController.SearchAPI(value);
            ObservableList<AppointmentTM> tableData = FXCollections.observableArrayList();
            for (Appointment appointment : appointments) {
                tableData.add(new AppointmentTM(
                        appointment.getCustId(),
                        appointment.getCustName(),
                        appointment.getAppointment(),
                        appointment.getTime(),
                        appointment.getDate()

                ));
            }
            tblAppointment.getItems().setAll(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate_Text(map,btnAddAppointment);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success ..").showAndWait();
            }
        }
    }


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[|v|V]|[0-9]{12})$");

    private void storeValidations() {
        map.put(txtCustomerID, nicPattern);
    }


}
