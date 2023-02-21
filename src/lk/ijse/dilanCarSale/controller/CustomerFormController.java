package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.model.CustomerModel;
import lk.ijse.dilanCarSale.model.SupplierModel;
import lk.ijse.dilanCarSale.to.Customer;
import lk.ijse.dilanCarSale.to.Supplier;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane paneCustomer;
    public JFXButton btnAdd;
    public JFXButton btnSearch;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnViewCusReport;
    public Label lblId;
    public Label lblName;
    public Label lblAddress;
    public Label lblContact;
    public Label lblEmail;
    public Label lblNic;
    @FXML
    private JFXTextField txtCusId;

    @FXML
    private JFXTextField txtCusName;

    @FXML
    private JFXTextField txtCusAddress;

    @FXML
    private JFXTextField txtCusContact;

    @FXML
    private JFXTextField txtCusEmail;

    @FXML
    private JFXTextField txtCusNic;

    public Matcher cusIdMatcher;
    public Matcher cusNameMatcher;
    public Matcher cusAddressMatcher;
    public Matcher cusContactMatcher;
    public Matcher cusEmailMatcher;
    public Matcher cusNicMatcher;

    public void btnAddOnAction(ActionEvent actionEvent) {
        try{
        String cusId=txtCusId.getText();
        String cusName=txtCusName.getText();
        String cusAddress=txtCusAddress.getText();
        int cusContact= Integer.parseInt(txtCusContact.getText());
        String cusEmail=txtCusEmail.getText();
        String cusNic=txtCusNic.getText();

        Customer customer=new Customer(cusId, cusName, cusAddress, cusContact, cusEmail, cusNic);

        try{
            boolean isAdded = CustomerModel.saveCustomer(customer);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "OOps!Try again!").show();
            }

        }catch (ClassNotFoundException | SQLException ex){
            new Alert(Alert.AlertType.WARNING, "Input Correct Details!").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String cusId=txtCusId.getText();
        try {
            Customer customer= CustomerModel.searchCustomer(cusId);

            if (customer != null) {
                txtCusName.setText(customer.getName());
                txtCusAddress.setText(customer.getAddress());
                txtCusContact.setText(String.valueOf(customer.getContact()));
                txtCusEmail.setText(customer.getEmail());
                txtCusNic.setText(customer.getNic());
            }else{
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Details!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try{
        String supId=txtCusId.getText();
        String supName=txtCusName.getText();
        String supAddress=txtCusAddress.getText();
        int supContact= Integer.parseInt(txtCusContact.getText());
        String supEmail=txtCusEmail.getText();
        String supNic=txtCusNic.getText();

        Customer customer=new Customer(supId, supName, supAddress, supContact, supEmail, supNic);

        try{

            boolean isUpdated = CustomerModel.updateCustomer(customer);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully!").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "OOps!Try again!").show();
            }

        }catch (ClassNotFoundException | SQLException ex){
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Details!").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String cusId=txtCusId.getText();

        try {
            boolean isDeleted= CustomerModel.deleteCustomer(cusId);

            if (isDeleted) {
                new Alert(Alert.AlertType.WARNING, "Deleted Successfully!").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Details!").show();
        }
    }

    public void btnViewCusReportOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/CustomerDetails.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtCusIdOnAction(ActionEvent actionEvent) {
        txtCusName.requestFocus();
    }

    public void txtCusNameOnAction(ActionEvent actionEvent) {
        txtCusAddress.requestFocus();
    }

    public void txtCusAddressOnAction(ActionEvent actionEvent) {
        txtCusContact.requestFocus();
    }

    public void txtCusContactOnAction(ActionEvent actionEvent) {
        txtCusEmail.requestFocus();
    }

    public void txtCusEmailOnAction(ActionEvent actionEvent) {
        txtCusNic.requestFocus();
    }

    public void txtCusNicOnAction(ActionEvent actionEvent) {

    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {
        lblId.setText("");
        txtCusId.setFocusColor(Paint.valueOf("Blue"));

        Pattern namePattern = Pattern.compile("^(C|c)([000-999]){3}$");
        cusIdMatcher = namePattern.matcher(txtCusId.getText());

        if (!cusIdMatcher.matches()) {
            txtCusId.requestFocus();
            txtCusId.setFocusColor(Paint.valueOf("Red"));
            lblId.setText("Invalid Id!");
        }
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        lblName.setText("");
        txtCusName.setFocusColor(Paint.valueOf("Blue"));

        Pattern namePattern = Pattern.compile("^[a-zA-Z0-9]{4,}$");
        cusNameMatcher = namePattern.matcher(txtCusName.getText());

        if(!cusNameMatcher.matches()) {
            txtCusName.requestFocus();
            txtCusName.setFocusColor(Paint.valueOf("Red"));
            lblName.setText("Invalid Name!");
        }
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        lblAddress.setText("");
        txtCusAddress.setFocusColor(Paint.valueOf("Blue"));

        Pattern addressPattern = Pattern.compile("^[0-9a-zA-Z]{4,}.$");
        cusAddressMatcher = addressPattern.matcher(txtCusAddress.getText());

        if(!cusAddressMatcher.matches()) {
            txtCusAddress.requestFocus();
            txtCusAddress.setFocusColor(Paint.valueOf("Red"));
            lblAddress.setText("Invalid Address!");
        }
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        lblContact.setText("");
        txtCusContact.setFocusColor(Paint.valueOf("Blue"));

        Pattern contactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        cusContactMatcher = contactPattern.matcher(txtCusContact.getText());

        if(!cusContactMatcher.matches()) {
            txtCusContact.requestFocus();
            txtCusContact.setFocusColor(Paint.valueOf("Red"));
            lblContact.setText("Invalid Contact Number!");
        }
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        lblEmail.setText("");
        txtCusEmail.setFocusColor(Paint.valueOf("Blue"));

        Pattern emailPattern = Pattern.compile("^([a-z0-9]{2,})([@])([a-z]{2,9})([.])([a-z]{2,})$");
        cusEmailMatcher = emailPattern.matcher(txtCusEmail.getText());

        if(!cusEmailMatcher.matches()) {
            txtCusEmail.requestFocus();
            txtCusEmail.setFocusColor(Paint.valueOf("Red"));
            lblEmail.setText("Invalid Email Address!");
        }
    }

    public void txtNicOnKeyReleased(KeyEvent keyEvent) {
        lblNic.setText("");
        txtCusNic.setFocusColor(Paint.valueOf("Blue"));

        Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}(v)$");
        cusNicMatcher = nicPattern.matcher(txtCusNic.getText());

        if(!cusNicMatcher.matches()) {
            txtCusNic.requestFocus();
            txtCusNic.setFocusColor(Paint.valueOf("Red"));
            lblNic.setText("Invalid NIC Number!");
        }
    }
}
