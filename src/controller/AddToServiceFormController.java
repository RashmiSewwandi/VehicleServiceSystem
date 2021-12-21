package controller;

import Model.AddToService;

import Model.Vehicle;
import animatefx.animation.BounceInDown;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ValidationUtil;
import views.tm.AddToServiceTM;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class AddToServiceFormController implements Initializable {

    public JFXTextField txtSID;
    public JFXTextField txtVehicleNumber;
    public JFXTextField txtVehicleModel;
    public JFXTextField txtFullType;
    public JFXComboBox<String> cmbServiceType;

    public TableView<AddToServiceTM> tblAddtoService;

    public JFXButton btnAdd;
    public JFXButton btnDelete;

    public TableColumn colSID;
    public TableColumn colVN;
    public TableColumn colVM;
    public TableColumn colFT;
    public TableColumn colST;
    public TextField txtSearchVehicleNumber;


    public void initialize(URL location, ResourceBundle resources)  {
        storeValidations();
        btnAdd.setDisable(true);

        txtSearchVehicleNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        colSID.setStyle("-fx-alignment: CENTER;");
        colVM.setStyle("-fx-alignment: CENTER;");
        colVN.setStyle("-fx-alignment: CENTER;");
        colFT.setStyle("-fx-alignment: CENTER;");
        colST.setStyle("-fx-alignment: CENTER;");
        tblAddtoService.setStyle("-fx-background-color: blue;");


        colSID.setCellValueFactory(new PropertyValueFactory<>("SId"));
        colVN.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVM.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        colFT.setCellValueFactory(new PropertyValueFactory<>("fualType"));
        colST.setCellValueFactory(new PropertyValueFactory<>("serviceType"));


        try {
            tblAddtoService(new ServiceController().getAllServic());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        serviceType();
    }


    private void tblAddtoService(ArrayList<AddToService> services) {
        ObservableList<AddToServiceTM> obList = FXCollections.observableArrayList();
        services.forEach(e -> {
            obList.add(
                    new AddToServiceTM(e.getSId(), e.getVehicleNumber(), e.getVehicleModel(), e.getFualType(), e.getServiceType()));
        });
        tblAddtoService.setItems(obList);
    }


    public void Add_To_Service_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        AddToService s1 = new AddToService(
                txtSID.getText(),
                txtVehicleNumber.getText(),
                txtVehicleModel.getText(),
                txtFullType.getText(),
                cmbServiceType.getValue()
        );

        if (new ServiceController().saveService(s1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Add To Service..").show();
            tblAddtoService(new ServiceController().getAllServic());
            clearField();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }



    public void Delete_Service_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        AddToServiceTM service = tblAddtoService.getSelectionModel().getSelectedItem();
        String customerName = service.getSId();

        if (new ServiceController().deleteService(customerName)) {
            new Alert(Alert.AlertType.INFORMATION, "Delete Complete").show();
            tblAddtoService(new ServiceController().getAllServic());
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
        clearField();
    }


    private void clearField() {
        txtSID.clear();
        txtVehicleNumber.clear();
        txtVehicleModel.clear();
        txtFullType.clear();
        cmbServiceType.requestFocus();
    }


    private void serviceType() {
        cmbServiceType.getItems().addAll("Reparing", "Cleaning", "Full Service");
        cmbServiceType.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
        }));
    }


    public void moveToVehicleNumber(ActionEvent actionEvent) {
        txtVehicleNumber.requestFocus();
    }


    public void Vehicle_Search_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vehicleNumber = txtVehicleNumber.getText();
        Vehicle vehicle = new VehicleController().searchVehicle(vehicleNumber);
        if (vehicle == null) {
            new Alert(Alert.AlertType.WARNING, "Try again");
        } else {
            setData(vehicle);
        }
    }


    void setData(Vehicle vehicle) {
        txtVehicleNumber.setText(vehicle.getVehicleNumber());
        txtVehicleModel.setText(vehicle.getVehicleModel());
        txtFullType.setText(vehicle.getFualType());
    }


    public void table_Click_OnAction(MouseEvent mouseEvent) {
        AddToServiceTM service = null;
        service = tblAddtoService.getSelectionModel().getSelectedItem();
        txtSID.setText(service.getSId());
        txtVehicleNumber.setText(service.getVehicleNumber());
        txtVehicleModel.setText(service.getVehicleModel());
        txtFullType.setText(service.getFualType());
        cmbServiceType.setValue(service.getServiceType());
    }


    public void Search_VNumber_OnAction(ActionEvent actionEvent) {
        search(txtSearchVehicleNumber.getText());
    }


    private void search(String value) {
        try {
            List<AddToService> addToServices = ServiceController.searchVehicleNumber(value);
            ObservableList<AddToServiceTM> tableData = FXCollections.observableArrayList();
            for (AddToService addToService : addToServices) {
                tableData.add(new AddToServiceTM(
                        addToService.getSId(),
                        addToService.getVehicleNumber(),
                        addToService.getVehicleModel(),
                        addToService.getFualType(),
                        addToService.getServiceType()
                ));
            }
            tblAddtoService.getItems().setAll(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void Full_Service_OnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/PackageForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        new BounceInDown(load).play();
    }


    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAdd);
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
    Pattern VehicleNumberPattern = Pattern.compile("^[A-Z]{2,3}[-][0-9]{4}$");


    private void storeValidations() {
        map.put(txtSID, SIDPattern);
        map.put(txtVehicleNumber, VehicleNumberPattern);
    }

}
