package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.model.StockModel;
import lk.ijse.dilanCarSale.model.SupplierModel;
import lk.ijse.dilanCarSale.tm.StockTM;
import lk.ijse.dilanCarSale.tm.SupplierTM;
import lk.ijse.dilanCarSale.to.Supplier;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplierFormController implements Initializable {
    public AnchorPane paneSupplier;
    public JFXButton btnAddSupplier;
    public JFXButton btnSearchSupplier;
    public JFXButton btnUpdateSupplier;
    public JFXButton btnDeleteSupplier;
    public JFXButton btnViewSupReport;
    public Label lblId;
    public Label lblName;
    public Label lblAddress;
    public Label lblContact;
    public Label lblEmail;
    public Label lblNic;
    @FXML
    private TableView<SupplierTM> tblSupplier;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colEmail;
    public TableColumn colNic;
    @FXML
    private JFXTextField txtSupId;

    @FXML
    private JFXTextField txtSupName;

    @FXML
    private JFXTextField txtSupAddress;

    @FXML
    private JFXTextField txtSupContact;

    @FXML
    private JFXTextField txtSupEmail;

    @FXML
    private JFXTextField txtSupNic;
    public Matcher supIdMatcher;
    public Matcher supNameMatcher;
    public Matcher supAddressMatcher;
    public Matcher supContactMatcher;
    public Matcher supEmailMatcher;
    public Matcher supNicMatcher;

    public ObservableList<SupplierTM> tm = FXCollections.observableArrayList();

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {

        try {
            String supId = txtSupId.getText();
            String supName = txtSupName.getText();
            String supAddress = txtSupAddress.getText();
            int supContact = Integer.parseInt(txtSupContact.getText());
            String supEmail = txtSupEmail.getText();
            String supNic = txtSupNic.getText();


        Supplier supplier = new Supplier(supId, supName, supAddress, supContact, supEmail, supNic);

        try {

            boolean isAdded = SupplierModel.saveSupplier(supplier);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Added Successfully!").show();
                setTableData();
                clearTextField();
            } else {
                new Alert(Alert.AlertType.ERROR, "OOps!Try again!").show();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Please Input Correct Data!").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnSearchSupplierOnAction(ActionEvent actionEvent) {
        String supId = txtSupId.getText();
        try {
            Supplier supplier = SupplierModel.searchSupplier(supId);

            if (supplier != null) {
                txtSupName.setText(supplier.getSupName());
                txtSupAddress.setText(supplier.getSupAddress());
                txtSupContact.setText(String.valueOf(supplier.getSupContact()));
                txtSupEmail.setText(supplier.getSupEmail());
                txtSupNic.setText(supplier.getSupNic());
                setTableData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id You Want!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Please Input Correct Data!").show();
        }
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {
        try{
        String supId = txtSupId.getText();
        String supName = txtSupName.getText();
        String supAddress = txtSupAddress.getText();
        int supContact = Integer.parseInt(txtSupContact.getText());
        String supEmail = txtSupEmail.getText();
        String supNic = txtSupNic.getText();

        Supplier supplier = new Supplier(supId, supName, supAddress, supContact, supEmail, supNic);

        try {

            boolean isUpdated = SupplierModel.updateSupplier(supplier);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully!").show();
                setTableData();
                clearTextField();
            } else {
                new Alert(Alert.AlertType.WARNING, "OOps!Try again!").show();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!").show();
        }
    }catch (NumberFormatException e){
        new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
    }
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {
        String supId = txtSupId.getText();

        try {
            boolean isDeleted = SupplierModel.deleteSupplier(supId);

            if (isDeleted) {
                new Alert(Alert.AlertType.WARNING, "Deleted Successfully!").show();
                setTableData();
                clearTextField();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id You Want!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Please Input Correct Data!").show();
        }
    }

    public void btnViewSupReportOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/SupplierDetails.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtSupIdOnAction(ActionEvent actionEvent) {
        txtSupName.requestFocus();
    }

    public void txtSupNameOnAction(ActionEvent actionEvent) {
        txtSupAddress.requestFocus();
    }

    public void txtSupAddressOnAction(ActionEvent actionEvent) {
        txtSupContact.requestFocus();
    }

    public void txtSupContactOnAction(ActionEvent actionEvent) {
        txtSupEmail.requestFocus();
    }

    public void txtSupEmailOnAction(ActionEvent actionEvent) {
        txtSupNic.requestFocus();
    }

    public void clearTextField() {
        txtSupId.clear();
        txtSupName.clear();
        txtSupAddress.clear();
        txtSupContact.clear();
        txtSupEmail.clear();
        txtSupNic.clear();
    }

    public void txtIdKeyReleasedOnAction(KeyEvent keyEvent) {
        lblId.setText("");
        txtSupId.setFocusColor(Paint.valueOf("Blue"));

        Pattern userNamePattern = Pattern.compile("^(SUP|sup)([000-999]){3}$");
        supIdMatcher = userNamePattern.matcher(txtSupId.getText());

        if (!supIdMatcher.matches()) {
            txtSupId.requestFocus();
            txtSupId.setFocusColor(Paint.valueOf("Red"));
            lblId.setText("Invalid Id!");
        }
    }

    public void txtNameKeyReleasedOnAction(KeyEvent keyEvent) {
        lblName.setText("");
        txtSupName.setFocusColor(Paint.valueOf("Blue"));

        Pattern namePattern = Pattern.compile("^[a-zA-Z0-9]{4,}$");
        supNameMatcher = namePattern.matcher(txtSupName.getText());

        if (!supNameMatcher.matches()) {
            txtSupName.requestFocus();
            txtSupName.setFocusColor(Paint.valueOf("Red"));
            lblName.setText("Invalid Name!");
        }
    }

    public void txtAddressKeyReleasedOnAction(KeyEvent keyEvent) {
        lblAddress.setText("");
        txtSupAddress.setFocusColor(Paint.valueOf("Blue"));

        Pattern addressPattern = Pattern.compile("^[0-9a-zA-Z]{4,}.$");
        supAddressMatcher = addressPattern.matcher(txtSupAddress.getText());

        if (!supAddressMatcher.matches()) {
            txtSupAddress.requestFocus();
            txtSupAddress.setFocusColor(Paint.valueOf("Red"));
            lblAddress.setText("Invalid Address!");
        }
    }

    public void txtContactKeyReleasedOnAction(KeyEvent keyEvent) {
        lblContact.setText("");
        txtSupContact.setFocusColor(Paint.valueOf("Blue"));

        Pattern contactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        supContactMatcher = contactPattern.matcher(txtSupContact.getText());

        if (!supContactMatcher.matches()) {
            txtSupContact.requestFocus();
            txtSupContact.setFocusColor(Paint.valueOf("Red"));
            lblContact.setText("Invalid Contact Number!");
        }
    }

    public void txtEmailKeyReleasedOnAction(KeyEvent keyEvent) {
        lblEmail.setText("");
        txtSupEmail.setFocusColor(Paint.valueOf("Blue"));

        Pattern emailPattern = Pattern.compile("^([a-z0-9]{2,})([@])([a-z]{2,9})([.])([a-z]{2,})$");
        supEmailMatcher = emailPattern.matcher(txtSupEmail.getText());

        if (!supEmailMatcher.matches()) {
            txtSupEmail.requestFocus();
            txtSupEmail.setFocusColor(Paint.valueOf("Red"));
            lblEmail.setText("Invalid Email Address!");
        }
    }

    public void txtNicKeyReleasedOnAction(KeyEvent keyEvent) {
        lblNic.setText("");
        txtSupNic.setFocusColor(Paint.valueOf("Blue"));

        Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}(v)$");
        supNicMatcher = nicPattern.matcher(txtSupNic.getText());

        if (!supNicMatcher.matches()) {
            txtSupNic.requestFocus();
            txtSupNic.setFocusColor(Paint.valueOf("Red"));
            lblNic.setText("Invalid NIC Number!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("supEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("supNic"));
        setTableData();
    }

    private void setTableData() {
        tblSupplier.getItems().clear();
        try {
            ResultSet set = SupplierModel.getAllData();
            while (set.next()) {
                tm.add(new SupplierTM(
                        set.getString(1),
                        set.getString(2),
                        set.getString(3),
                        set.getInt(4),
                        set.getString(5),
                        set.getString(6)

                ));
            }
            tblSupplier.setItems(tm);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
