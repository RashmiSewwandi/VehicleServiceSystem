package controller;

import Model.AddToService;
import Model.Customer;
import Model.Payment;
import Model.ServiceDetail;
import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import views.tm.CartTM;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class PaymentFormController  {
    public AnchorPane PaymentContext;

    public Label lblDate;
    public Label lblTime;

    public TextField txtName;
    public TextField txtAddress;
    public Label lblPaymentId;

    public TextField txtTP;
    public TextField txtVehicleNumber;
    public TextField txtService;
    public TextField txtItemReplace;


    public TableView <CartTM>tblPayment;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleType;
    public TableColumn colService;
    public TableColumn colItem;
    public TableColumn colServiceCharge;

    public TextField txtCustomerNIC;
    public TextField txtVehicleModel;
    public ComboBox<String> cmbFullService;
    public JFXButton btnBill;

    int cartSelectedRowForRemove = -1;

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void initialize() {

        colVehicleNumber.setStyle("-fx-alignment: CENTER;");
        colVehicleType.setStyle("-fx-alignment: CENTER;");
        colService.setStyle("-fx-alignment: CENTER;");
        colItem.setStyle("-fx-alignment: CENTER;");

        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        colService.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemReplace"));
        colServiceCharge.setCellValueFactory(new PropertyValueFactory<>("serviceCharge"));

        loadDateAndTime();
        serviceFull();
        setPaymentId();

        tblPayment.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;

        });

    }

    private void serviceFull() {
        cmbFullService.getItems().addAll("Full Service   ----------------------------","SUZUKI"," Rs.9500","HONDA" ," Rs.11400","TOYOTA" ," Rs.10900","Reparing  ---------------------------","Small- Rs.400","Large- Rs.500","Ex.Large- Rs.600",
        "Cleaning -----------------------","Small- Rs.300","Large- Rs.500","Ex.Large- Rs.600");
        cmbFullService.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
        }));
    }



    ObservableList<CartTM> obList = FXCollections.observableArrayList();
    public void Add_To_Card_OnAction(ActionEvent actionEvent) {

        String vehicleModel = txtVehicleModel.getText();
        String serviceType = txtService.getText();
        String itemReplace = txtItemReplace.getText();
        String serviceCharge = cmbFullService.getValue();



        CartTM tm = new CartTM(
                txtVehicleNumber.getText(),
                vehicleModel,
                serviceType,
                itemReplace,
                cmbFullService.getValue()

        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {// new Add item row
            obList.add(tm);
        } else {
            // update
            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getVehicleNumber(),
                    temp.getVehicleModel(),
                    serviceType,
                    temp.getItemReplace(),
                    cmbFullService.getValue()

            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }

        tblPayment.setItems(obList);
    }

    private int isExists(CartTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getVehicleNumber().equals(obList.get(i).getVehicleNumber())) {
                return i;
            }
        }
        return -1;
    }


    public void Delete_On_Action(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            tblPayment.refresh();
        }
    }

    public void Print_On_Action(ActionEvent actionEvent) {
        ArrayList<ServiceDetail> service = new ArrayList<>();
        for (CartTM tempTm:obList
        ) {

            service.add(new ServiceDetail(tempTm.getVehicleNumber(),tempTm.getServiceType(),tempTm.getItemReplace(),
                    tempTm.getServiceCharge()));
        }

        Payment payment= new Payment(
                lblPaymentId.getText(),
                txtCustomerNIC.getText(),
                lblDate.getText(),
                lblTime.getText(),
                service
        );

        if (new PaymentController().Payment(payment)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setPaymentId();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

    }



    public void serch_Customer_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtCustomerNIC.getText();
        Customer customer = new CustomerController().searchCustomer(customerId);
        if (customer == null) {
            new Alert(Alert.AlertType.WARNING,"Try again");
        }else{
            setData(customer);
        }

    }

    void setData(Customer customer) {
        txtName.setText(customer.getCustName());
        txtAddress.setText(customer.getCustAddress());
        txtTP.setText(customer.getContactNumber());
    }


    public void Search_Vehicle_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String vnumber = txtVehicleNumber.getText();
        AddToService service = new ServiceController().searchService(vnumber);
        if (service == null) {
            new Alert(Alert.AlertType.WARNING,"Empty");
        }else{
            setData(service);
        }
    }

    private void setData(AddToService service) {
        txtVehicleModel.setText(service.getVehicleModel());
        txtService.setText(service.getServiceType());
    }

    private void setPaymentId() {
        try {
            lblPaymentId.setText(new PaymentController().getPaymentId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void print_Bill_Event(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/views/reports/Payment.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            String VID = txtVehicleNumber.getText();

            HashMap map = new HashMap();
            map.put("Search_Vehicle_ID", VID);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
