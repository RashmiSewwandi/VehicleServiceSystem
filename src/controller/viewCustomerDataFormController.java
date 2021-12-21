package controller;


import Model.Customer;

import com.jfoenix.controls.JFXButton;
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
import views.tm.CustomerTM;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class viewCustomerDataFormController implements Initializable {

    public TableView <CustomerTM>tblCustomer;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContactNumber;
    public TableColumn colDate;
    public TableColumn colMail;
    public TableColumn colNIC;
    
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public Label lblDate;
    public JFXButton btnAddCustomer;
    public TextField txtSearch;

    public TextField txtNIC;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContactNumber;
    public TextField txtMail;


    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
    }

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern nicPattern = Pattern.compile("^([0-9]{9}[|v|V]|[0-9]{12})$");
    Pattern namePattern = Pattern.compile("^[A-z. ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ,]{6,30}[.]?$");
    Pattern contactNumberPattern = Pattern.compile("^([+]\\d{2})?\\d{10}$");
    Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");


    private void storeValidations() {
        map.put(txtNIC, nicPattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContactNumber, contactNumberPattern);
        map.put(txtMail, emailPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate_WithPane(map,btnAddCustomer);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.CONFIRMATION, "Success ..").showAndWait();
            }

        }
    }



    public void initialize(URL location, ResourceBundle resources) {
        loadDate();
        storeValidations();
        btnAddCustomer.setDisable(true);

        colNIC.setStyle("-fx-alignment: CENTER;");
        colContactNumber.setStyle("-fx-alignment: CENTER;");
        colDate.setStyle("-fx-alignment: CENTER;");
        tblCustomer.setStyle("-fx-background-color: blue;");


        colNIC.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

        try {
            addCustomertoTable(new CustomerController().getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }



    public void addCustomertoTable(ArrayList<Customer> customers)  {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        customers.forEach(e->{
            obList.add(
                    new CustomerTM(e.getCustId(),e.getCustName(),e.getCustAddress(),e.getContactNumber(),e.getEmail(),e.getDate()));
        });
        tblCustomer.setItems(obList);
    }




    public void btnDeleteCustomer_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerTM customer = tblCustomer.getSelectionModel().getSelectedItem();
        String customerName =customer.getCustId();

        if (new CustomerController().deleteCustomer(customerName)){
            new Alert(Alert.AlertType.INFORMATION,"Delete Complete").show();
            addCustomertoTable(new CustomerController().getAllCustomers());
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
        clearField();
    }



    private void clearField(){
        txtNIC.clear();
        txtName.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtMail.clear();
    }



    public void btnUpdateCustomer_OnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        CustomerTM customer = new CustomerTM(
                txtNIC.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContactNumber.getText(),
                txtMail.getText(),
                lblDate.getText()
        );
        if(new CustomerController().updateCustomer(customer)){
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            addCustomertoTable(new CustomerController().getAllCustomers());
            clearField();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }



    public void table_Click_OnAction(MouseEvent mouseEvent) {
        CustomerTM customer = null;
        customer = tblCustomer.getSelectionModel().getSelectedItem();
        txtNIC.setText(customer.getCustId());
        txtName.setText(customer.getCustName());
        txtAddress.setText(customer.getCustAddress());
        txtContactNumber.setText(customer.getContactNumber());
        txtMail.setText(customer.getEmail());
        lblDate.setText(customer.getDate());
    }


    

    public void btnAddCustomer_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer c1 = new Customer(
                txtNIC.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtContactNumber.getText(),
                txtMail.getText(),
                lblDate.getText()
        );

        if(new CustomerController().saveCustomer(c1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            addCustomertoTable(new CustomerController().getAllCustomers());
            clearField();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }



    public void Search_Customer(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }


    private void search(String value) {
        try {
            List<Customer> customers = CustomerController.searchNIC(value);
            ObservableList<CustomerTM> tableData = FXCollections.observableArrayList();
            for (Customer customer : customers) {
                tableData.add(new CustomerTM(
                        customer.getCustId(),
                        customer.getCustName(),
                        customer.getCustAddress(),
                        customer.getContactNumber(),
                        customer.getEmail(),
                        customer.getDate()
                ));
            }
            tblCustomer.getItems().setAll(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
