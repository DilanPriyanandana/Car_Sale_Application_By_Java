package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.dilanCarSale.to.Vehicle;
import lk.ijse.dilanCarSale.util.Navigation;
import lk.ijse.dilanCarSale.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import static lk.ijse.dilanCarSale.model.EmployeeModel.getEmployeeCount;
import static lk.ijse.dilanCarSale.model.VehicleModel.getVehicleCount;

public class ManagerFormController implements Initializable {
    public AnchorPane paneManager;
    public AnchorPane paneMangerTasks;
    public JFXButton btnDashboard;
    public JFXButton btnVehicle;
    public JFXButton btnSupplier;
    public JFXButton btnStock;
    public JFXButton btnPayment;
    public AnchorPane paneDashboard;
    public Label lblDate;
    public Text txtLiveClock;
    public Text txtDate;
    public Text tctEmployeeCount;
    public Text txtVehicleCount;

    int minute;
    int hour;
    int second;

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DASHBOARD,paneDashboard);
    }

    public void btnVehicleOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.VEHICLE,paneDashboard);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,paneDashboard);
    }

    public void btnStockOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.STOCK,paneDashboard);
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PAYMENT,paneDashboard);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        employeeCount();
        vehicleCount();
        setLocalDate();
        realTimeClock();
    }

    private void realTimeClock() {
        Thread clock = new Thread() {
            public void run() {
                for (;;) {
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    Calendar cal = Calendar.getInstance();

                    second = cal.get(Calendar.SECOND);
                    minute = cal.get(Calendar.MINUTE);
                    hour = cal.get(Calendar.HOUR);
                    //System.out.println(hour + ":" + (minute) + ":" + second);
                    txtLiveClock.setText(hour + ":" + (minute) + ":" + second);

                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {

                    }
                }
            }
        };
        clock.start();
    }

    private void setLocalDate() {
        txtDate.setText(String.valueOf(LocalDate.now()));

    }

    private void vehicleCount() {
        try {
            txtVehicleCount.setText(String.valueOf(getVehicleCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void employeeCount() {
        try {
            tctEmployeeCount.setText(String.valueOf(getEmployeeCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
