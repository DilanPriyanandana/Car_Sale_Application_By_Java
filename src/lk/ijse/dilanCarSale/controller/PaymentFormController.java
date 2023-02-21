package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.model.EmployeeModel;
import lk.ijse.dilanCarSale.model.EmployeePaymentModel;
import lk.ijse.dilanCarSale.to.EmployeePayment;
import lk.ijse.dilanCarSale.util.Navigation;
import lk.ijse.dilanCarSale.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentFormController implements Initializable {
    public JFXDatePicker dateEmpPayment;
    public JFXComboBox cmbEmpId;
    public JFXTimePicker timeEmpPayment;

    public AnchorPane panePayment;
    public JFXButton btnEmployeePayment;
    public JFXButton btnSupplierPayment;
    public TextField txtDesc;
    public JFXButton btnEmpPayment;
    public JFXButton btnEmpPaymentReport;
    public Label lblAmount;
    @FXML
    private JFXTextField txtAmount;
    public Matcher amountMatcher;


    public void btnSupplierPaymentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER_PAYMENT,panePayment);
    }

    public void btnEmpPaymentOnAction(ActionEvent actionEvent) {
        try{
        String empId= String.valueOf(cmbEmpId.getValue());
        String description=txtDesc.getText();
        double amount= Double.parseDouble(txtAmount.getText());
        LocalDate date=dateEmpPayment.getValue();
        LocalTime time=timeEmpPayment.getValue();

        EmployeePayment ep=new EmployeePayment(empId,description,amount,date,time);
        try{
            boolean isAdded = EmployeePaymentModel.savePayment(ep);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Payment Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "OOps!Try again!").show();
            }

        }catch (ClassNotFoundException | SQLException ex){
            new Alert(Alert.AlertType.WARNING, "Please Input All Data!").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnEmpPaymentReportOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/EmployeePayment.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void getEmployeeIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = EmployeeModel.loadEmployeeIds();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbEmpId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getEmployeeIds();
    }

    public void txtAmountOnKeyReleased(KeyEvent keyEvent) {
        lblAmount.setText("");
        txtAmount.setFocusColor(Paint.valueOf("Blue"));

        Pattern amountPattern = Pattern.compile("^[0-9]{4,7}$");
        amountMatcher = amountPattern.matcher(txtAmount.getText());

        if(!amountMatcher.matches()) {
            txtAmount.requestFocus();
            txtAmount.setFocusColor(Paint.valueOf("Red"));
            lblAmount.setText("Invalid Amount!");
        }
    }
}
