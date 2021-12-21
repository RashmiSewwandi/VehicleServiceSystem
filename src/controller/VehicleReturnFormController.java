package controller;

import Model.*;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.ValidationUtil;
import views.tm.VehicleReturnTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class VehicleReturnFormController implements Initializable {
    public Label lblDate;

    public TableView <VehicleReturnTM>tblVehicleReturn;
    public TableColumn colNIC;
    public TableColumn colCustomerName;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleModel;
    public TableColumn colDescription;
    public TableColumn colNextDate;

    public TextField txtNIC;
    public TextField txtCustomerName;
    public TextField txtContactNumber;
    public TextField txtVehicleNumber;
    public TextField txtVehicleModel;
    public TextField txtDescription;
    public TextField txtServiceType;
    public DatePicker txtNextDate;
    public JFXButton btnAdd;


    public void Payment_On_Action(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/PaymentForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }


    public void initialize(URL location, ResourceBundle resources)  {

        storeValidations();
        btnAdd.setDisable(true);

        colNIC.setStyle("-fx-alignment: CENTER;");
        colVehicleModel.setStyle("-fx-alignment: CENTER;");
        colVehicleNumber.setStyle("-fx-alignment: CENTER;");
        tblVehicleReturn.setStyle("-fx-background-color: blue;");

        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleModel.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colNextDate.setCellValueFactory(new PropertyValueFactory<>("nextDate"));


        try {
            addReturnTable(new ReturnController().getAllReturn());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDate();
    }


    public void addReturnTable(ArrayList<VehicleReturn> vehicleReturns)  {
        ObservableList<VehicleReturnTM> obList = FXCollections.observableArrayList();
        vehicleReturns.forEach(e->{
            obList.add(
                    new VehicleReturnTM(e.getNIC(),e.getCustName(),e.getVehicleNumber(),e.getVehicleModel(),e.getDescription(),e.getNextDate()));
        });
        tblVehicleReturn.setItems(obList);

    }


    public void Add_VehicleReturn_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        VehicleReturn v1 = new VehicleReturn(
                txtNIC.getText(),
                txtCustomerName.getText(),
                txtVehicleNumber.getText(),
                txtVehicleModel.getText(),
                txtDescription.getText(),
                txtNextDate.getValue().toString()
        );

        if(new ReturnController().saveReturn(v1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Return Success ...").show();
            addReturnTable(new ReturnController().getAllReturn());
            clearField();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    private void clearField() {
        txtNIC.clear();
        txtCustomerName.clear();
        txtVehicleNumber.clear();
        txtContactNumber.clear();
        txtServiceType.clear();
        txtVehicleModel.clear();
        txtDescription.clear();
        txtNIC.requestFocus();
    }

    public void Delete_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        VehicleReturnTM vehicleReturn = tblVehicleReturn.getSelectionModel().getSelectedItem();
        String vnum =vehicleReturn.getVehicleNumber();

        if (new ReturnController().deleteReturn(vnum)){
            new Alert(Alert.AlertType.INFORMATION,"Delete Complete").show();
            addReturnTable(new ReturnController().getAllReturn());
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
        clearField();
    }

    public void Search_Customer_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String NIC = txtNIC.getText();

        Customer customer = new CustomerController().searchCustomer(NIC);
        if (customer == null) {
            new Alert(Alert.AlertType.WARNING,"Empty");
        }else{
            setData(customer);
        }
    }


    private void setData(Customer customer) {
        txtNIC.setText(customer.getCustId());
        txtCustomerName.setText(customer.getCustName());
        txtContactNumber.setText(customer.getContactNumber());
    }


    public void Search_Vehicle_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vnumber1 = txtVehicleNumber.getText();

        AddToService service = new ServiceController().searchService(vnumber1);
        if (service == null) {
            new Alert(Alert.AlertType.WARNING,"Empty");
        }else{
            setData(service);
        }
    }



    private void setData(AddToService service) {
        txtVehicleModel.setText(service.getVehicleModel());
        txtServiceType.setText(service.getServiceType());
    }



    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate_Text(map,btnAdd);

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
    Pattern VehicleNumberPattern = Pattern.compile("^[A-Z]{2,3}[-][0-9]{4}$");

    private void storeValidations() {
        map.put(txtNIC, nicPattern);
        map.put(txtVehicleNumber,VehicleNumberPattern);
    }

}
