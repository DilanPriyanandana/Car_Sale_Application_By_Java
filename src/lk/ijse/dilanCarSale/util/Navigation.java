package lk.ijse.dilanCarSale.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    public static void navigate(Routes routes, AnchorPane pane) throws IOException {

       Navigation.pane=pane;
       Navigation.pane.getChildren().clear();
       Stage window = (Stage) Navigation.pane.getScene().getWindow();

       switch (routes){

           case SIGN_UP:
               window.setTitle("Sign Up");
               unitUi("SignUpForm.fxml");
               break;
           case LOGIN:
               window.setTitle("Login");
               unitUi("LoginForm.fxml");
               break;
           case MANAGER:
               window.setTitle("Manager");
               unitUi("ManagerForm.fxml");
               break;
           case DASHBOARD:
               window.setTitle("Dashboard");
               unitUi("DashBoardForm.fxml");
               break;
           case SUPPLIER:
               window.setTitle("Supplier");
               unitUi("SupplierForm.fxml");
               break;
           case VEHICLE:
               window.setTitle("Vehicle");
               unitUi("VehicleForm.fxml");
               break;
           case PAYMENT:
               window.setTitle("Payment");
               unitUi("PaymentForm.fxml");
               break;
           case STOCK:
               window.setTitle("Stock");
               unitUi("StockForm.fxml");
               break;
           case EMPLOYEE:
               window.setTitle("Employee");
               unitUi("EmployeeForm.fxml");
               break;
           case VIEW_AVAILABILITY:
               window.setTitle("View Availability");
               unitUi("ViewAvailabilityForm.fxml");
               break;
           case PLACE_ORDER:
               window.setTitle("Place Order");
               unitUi("PlaceOrderForm.fxml");
               break;
           case CUSTOMER:
               window.setTitle("Customer");
               unitUi("CustomerForm.fxml");
               break;
           case DASHBOARD_EMPLOYEE:
               window.setTitle("DashBoard Employee");
               unitUi("DashBoardEmployeeForm.fxml");
               break;
           case AFTER_LOGIN:
               window.setTitle("Login Form");
               unitUi("AfterLoginForm.fxml");
               break;
           case STOCK_DETAILS:
               window.setTitle("Stock Details");
               unitUi("StockDetailsForm.fxml");
               break;
           case SUPPLIER_PAYMENT:
               window.setTitle("Supplier Payment");
               unitUi("SupplierPaymentForm.fxml");
               break;

           default:
               new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
            }


    }

    private static void unitUi(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/dilanCarSale/view/"+location)));


    }
}
