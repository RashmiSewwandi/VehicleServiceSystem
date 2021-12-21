package controller;

import Model.Appointment;
import Model.Vehicle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import views.tm.VehicleTM;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;



public class AddVehicleFormController implements Initializable {

    public JFXComboBox<String> cmbFullType;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleModel;
    public Label lblDate;

    public TableView<VehicleTM> tblVehicle;
    public TableColumn colAppointment;
    public TableColumn colVehicleModel;
    public TableColumn colVehicleNumber;
    public TableColumn colDate;
    public TableColumn colFualType;
    public TableColumn colCustomerName;

    public JFXTextField txtAppointmentNumber;
    public JFXTextField txtNIC;
    public JFXTextField txtCustomerName;
    public JFXButton btnAddVehicle;
    public TextField txtSearch;

    private void loadDate(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    public void initialize(URL location, ResourceBundle resources) {
        storeValidations();
        btnAddVehicle.setDisable(true);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });


        try {
            AddVehicleToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadDate();
        Vehicle();

    }


    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddVehicle);

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
    Pattern APIPattern = Pattern.compile("^[A|a]-[0-9]{3}$");
    Pattern VehicleNumberPattern = Pattern.compile("^[A-Z]{2,3}[-][0-9]{4}$");
    Pattern VehicleTypePattern = Pattern.compile("^[A-Z,a-z]{3,}$");

    private void storeValidations() {
        map.put(txtAppointmentNumber, APIPattern);
        map.put(txtVehicleNumber, VehicleNumberPattern);
        map.put(txtVehicleModel, VehicleTypePattern);

    }



    public void AddVehicleToTable() throws SQLException, ClassNotFoundException {
        ObservableList<VehicleTM> vehicle = new VehicleController().getVehicleList();

        colAppointment.setStyle("-fx-alignment: CENTER;");
        colVehicleModel.setStyle("-fx-alignment: CENTER;");
        colVehicleNumber.setStyle("-fx-alignment: CENTER;");
        colFualType.setStyle("-fx-alignment: CENTER;");
        colDate.setStyle("-fx-alignment: CENTER;");
        tblVehicle.setStyle("-fx-background-color: blue;");


        colAppointment.setCellValueFactory(new PropertyValueFactory<>("appointment"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleModel.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        colFualType.setCellValueFactory(new PropertyValueFactory<>("fualType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tblVehicle.setItems(vehicle);
    }



    ObservableList<VehicleTM> obList = FXCollections.observableArrayList();
    public void vehicle_Add_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(
                txtAppointmentNumber.getText(),
                txtCustomerName.getText(),
                txtVehicleNumber.getText(),
                txtVehicleModel.getText(),
                cmbFullType.getValue(),
                lblDate.getText()
        );
        if(new VehicleController().saveVehicle(vehicle)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved....");
            AddVehicleToTable();
            clearField();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again....");
        }

    }


    public void Delete_Vehicle_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        VehicleTM vehicle = tblVehicle.getSelectionModel().getSelectedItem();
        String vehicleNumber =vehicle.getVehicleNumber();

        if (new VehicleController().deleteVehicle(vehicleNumber)){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Success..").show();
            AddVehicleToTable();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }
        clearField();
    }



    private void Vehicle() {
        cmbFullType.getItems().addAll("Diesel","Petrol","Electonic" );
        cmbFullType.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
        }));
    }


    public void moveToVehicleModel(ActionEvent actionEvent) {
        txtVehicleModel.requestFocus();
    }


    public void Search_Customer_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String appointmentId = txtAppointmentNumber.getText();
        Appointment appointment = new AppointmentController().searchAppointment(appointmentId);
        if (appointment == null) {
            new Alert(Alert.AlertType.WARNING,"Try again");
        }else{
            setData(appointment);
        }
    }


    void setData(Appointment appointment) {
        txtNIC.setText(appointment.getCustId());
        txtCustomerName.setText(appointment.getCustName());
    }


    private void clearField(){
        txtAppointmentNumber.clear();
        txtNIC.clear();
        txtCustomerName.clear();
        txtVehicleNumber.clear();
        txtVehicleModel.clear();
        txtAppointmentNumber.requestFocus();
    }


    public void table_Click_OnAction(MouseEvent mouseEvent) {
        VehicleTM vehicle = null;
        vehicle = tblVehicle.getSelectionModel().getSelectedItem();
        txtAppointmentNumber.setText(vehicle.getAppointment());
        txtCustomerName.setText(vehicle.getCustName());
        txtVehicleNumber.setText(vehicle.getVehicleNumber());
        txtVehicleModel.setText(vehicle.getVehicleModel());
        cmbFullType.setValue(vehicle.getFualType());
        lblDate.setText(vehicle.getDate());
    }


    public void Search_Customer(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }


    private void search(String value) {
        try {
            List<Vehicle> vehicles = VehicleController.SearchName(value);
            ObservableList<VehicleTM> tableData = FXCollections.observableArrayList();
            for (Vehicle vehicle : vehicles) {
                tableData.add(new VehicleTM(
                        vehicle.getAppointment(),
                        vehicle.getCustName(),
                        vehicle.getVehicleNumber(),
                        vehicle.getVehicleModel(),
                        vehicle.getFualType(),
                        vehicle.getDate()

                ));
            }
            tblVehicle.getItems().setAll(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
