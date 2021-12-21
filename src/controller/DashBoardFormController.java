package controller;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;


public class DashBoardFormController  {
    public AnchorPane dashBoardContext;
    public JFXButton btnCustomer;
    public JFXButton btnAppointment;
    public JFXButton btnVehicle;

    public JFXButton btnEmployee;
    public JFXButton btnService;
    public JFXButton btnReturn;
    public JFXButton btnSalary;
    public JFXButton btnIncome;
    public JFXButton btnLogOut;
    public AnchorPane MainDashBoardContext;



    public void customerFormOnAction(ActionEvent actionEvent) throws IOException {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/viewCustomerDataForm.fxml"));
            pane = fxmlLoader.load();
            dashBoardContext.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void Open_AppointmentForm(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/AppointmentForm.fxml"));
            pane = fxmlLoader.load();
            dashBoardContext.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vehicle_Add_Form(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/AddVehicleForm.fxml"));
            pane = fxmlLoader.load();
            dashBoardContext.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Add_Employee_OnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/AddEmployeeForm.fxml"));
            pane = fxmlLoader.load();
            dashBoardContext.getChildren().setAll(pane);
            new ZoomIn(pane).play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vehicle_AddTo_Service_Form(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/AddToServiceForm.fxml"));
            pane = fxmlLoader.load();
            dashBoardContext.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vehicle_Return_Form(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/VehicleReturnForm.fxml"));
            pane = fxmlLoader.load();
            dashBoardContext.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Salary_On_Action(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/SalaryForm.fxml"));
            pane = fxmlLoader.load();
            dashBoardContext.getChildren().setAll(pane);
            new ZoomIn(pane).play();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void LogOut_On_Action(ActionEvent actionEvent) throws IOException {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../views/LogingForm.fxml"));
            pane = fxmlLoader.load();
            MainDashBoardContext.getChildren().setAll(pane);
            new SlideInDown(pane).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReportsFormOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../views/ReportForms.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
