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
import lk.ijse.dilanCarSale.model.SupplierModel;
import lk.ijse.dilanCarSale.model.SupplierPaymentModel;
import lk.ijse.dilanCarSale.to.SupplierPayment;
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

public class SupplierPaymentFormController implements Initializable {
    public AnchorPane paneSupplierPayment;
    public JFXButton btnEmployeePayment;
    public JFXButton btnSupplierPayment;
    public JFXComboBox cmbSupId;
    public TextField txtDesc;
    public JFXDatePicker dateSupPayment;
    public JFXTimePicker timeSupPayment;
    public JFXButton btnSupPayment;
    public JFXButton btnSupPaymentReport;
    public Label lblAmount;
    @FXML
    private JFXTextField txtAmount;
    public Matcher amountMatcher;

    public void btnEmployeePaymentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.PAYMENT, paneSupplierPayment);
    }

    public void btnSupPaymentOnAction(ActionEvent actionEvent) {
        try{
        String supId= String.valueOf(cmbSupId.getValue());
        String description=txtDesc.getText();
        double amount= Double.parseDouble(txtAmount.getText());
        LocalDate date=dateSupPayment.getValue();
        LocalTime time=timeSupPayment.getValue();

        SupplierPayment sp=new SupplierPayment(supId,description,amount,date,time);
        try{
            boolean isAdded = SupplierPaymentModel.savePayment(sp);
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

    public void btnSupPaymentReportOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/SupplierPayment.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getSupplierIds();

    }

    private void getSupplierIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = SupplierModel.loadSupplierIds();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbSupId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbSupIdOnAction(ActionEvent actionEvent) {
        txtDesc.requestFocus();
    }

    public void txtDescOnAction(ActionEvent actionEvent) {
        txtAmount.requestFocus();
    }

    public void txtAmountOnAction(ActionEvent actionEvent) {
        txtAmount.requestFocus();
    }

    public void dateSupPaymentOnAction(ActionEvent actionEvent) {
        dateSupPayment.requestFocus();
    }

    public void timeSupPaymentOnAction(ActionEvent actionEvent) {
        timeSupPayment.requestFocus();
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
