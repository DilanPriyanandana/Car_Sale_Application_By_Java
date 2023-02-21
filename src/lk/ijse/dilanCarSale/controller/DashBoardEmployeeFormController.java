package lk.ijse.dilanCarSale.controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import static lk.ijse.dilanCarSale.model.CustomerModel.getCustomerCount;
import static lk.ijse.dilanCarSale.model.VehicleModel.getVehicleCount;

public class DashBoardEmployeeFormController implements Initializable {
    public AnchorPane paneDashboardEmp;

    public Text txtLiveClock;
    public Text txtDate;
    public Text txtVehicleCount;
    public Text txtCustomerCount;

    int minute;
    int hour;
    int second;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerCount();
        vehicleCount();
        setLocalDate();
        realTimeClock();
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

    private void customerCount() {
        try {

            txtCustomerCount.setText(String.valueOf(getCustomerCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLocalDate() {
        txtDate.setText(String.valueOf(LocalDate.now()));

    }
}
