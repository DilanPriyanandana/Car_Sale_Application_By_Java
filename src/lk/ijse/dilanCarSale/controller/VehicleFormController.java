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
import javafx.scene.paint.Paint;
import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.model.StockModel;
import lk.ijse.dilanCarSale.model.VehicleModel;
import lk.ijse.dilanCarSale.tm.StockTM;
import lk.ijse.dilanCarSale.tm.VehicleTM;
import lk.ijse.dilanCarSale.to.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehicleFormController implements Initializable {
    public JFXButton btnAdd;
    public JFXButton btnSearch;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnViewVehReport;
    public Label lblId;
    public Label lblPrice;
    public Label lblDesc;
    public Label lblBrand;
    public Label lblFuel;
    public Label lblStockId;
    public JFXTextField txtVehQty;
    public Label lblQty;
    public TableColumn colVehId;
    public TableColumn colPrice;
    public TableColumn colDescription;
    public TableColumn colBrand;
    public TableColumn colFuel;
    public TableColumn colQty;
    public TableColumn colStockId;
    @FXML
    private TableView<VehicleTM> tblVehicle;

    public ObservableList<VehicleTM> tm = FXCollections.observableArrayList();
    @FXML
    private JFXTextField txtVehId;

    @FXML
    private JFXTextField txtVehPrice;

    @FXML
    private JFXTextField txtVehDescription;

    @FXML
    private JFXTextField txtVehBrand;

    @FXML
    private JFXTextField txtVehFuel;

    @FXML
    private JFXTextField txtStockId;

    public Matcher vehIdMatcher;
    public Matcher vehPriceMatcher;
    public Matcher vehDescriptionMatcher;
    public Matcher vehBrandMatcher;
    public Matcher vehFuelMatcher;
    public Matcher vehStockIdMatcher;
    public Matcher vehQtyMatcher;

    public void btnAddOnAction(ActionEvent actionEvent) {
        try{
        String vehID=txtVehId.getText();
        double price= Double.parseDouble(txtVehPrice.getText());
        String description=txtVehDescription.getText();
        String brand=txtVehBrand.getText();
        String fuel=txtVehFuel.getText();
        String stockId=txtStockId.getText();
        int qty= Integer.parseInt(txtVehQty.getText());

        Vehicle vehicle =new Vehicle(vehID, price, description, brand, fuel, stockId, qty);

        try{
            boolean isAdded = VehicleModel.saveVehicle(vehicle);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Vehicle Added Successfully!").show();
                setTableData();
            }else {
                new Alert(Alert.AlertType.WARNING, "OOps!Try again!").show();
            }

        }catch (ClassNotFoundException | SQLException ex){
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!!").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String vehId=txtVehId.getText();
        try {
            Vehicle vehicle= VehicleModel.searchVehicle(vehId);

            if (vehicle != null) {
                txtVehPrice.setText(String.valueOf(vehicle.getPrice()));
                txtVehDescription.setText(vehicle.getDescription());
                txtVehBrand.setText(vehicle.getBrand());
                txtVehFuel.setText(vehicle.getFuel());
                txtStockId.setText(vehicle.getStockId());
                txtVehQty.setText(String.valueOf(vehicle.getQty()));
            }else{
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try{
        String vehID=txtVehId.getText();
        double price= Double.parseDouble(txtVehPrice.getText());
        String description=txtVehDescription.getText();
        String brand=txtVehBrand.getText();
        String fuel=txtVehFuel.getText();
        String stockId=txtStockId.getText();
        int qty= Integer.parseInt(txtVehQty.getText());

        Vehicle vehicle =new Vehicle(vehID, price, description, brand, fuel, stockId, qty);

        try{

            boolean isAdded = VehicleModel.updateVehicle(vehicle);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Updated Successfully!").show();
                setTableData();
            }else {
                new Alert(Alert.AlertType.WARNING, "OOps!Try again!").show();
            }

        }catch (ClassNotFoundException | SQLException ex){
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String vehId=txtVehId.getText();

        try {
            boolean isDeleted= VehicleModel.deleteVehicle(vehId);

            if (isDeleted) {
                new Alert(Alert.AlertType.WARNING, "Deleted Successfully!").show();
                setTableData();
            }else{
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!!").show();
        }
    }

    public void btnViewVehReportOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/VehicleReport.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void txtVehIdOnKeyTyped(KeyEvent keyEvent) {

    }
    public void txtVehIdOnKeyReleased(KeyEvent keyEvent) {
        lblId.setText("");
        txtVehId.setFocusColor(Paint.valueOf("Blue"));

        Pattern idPattern = Pattern.compile("^(V|v)([000-999]){3}$");
        vehIdMatcher = idPattern.matcher(txtVehId.getText());

        if(!vehIdMatcher.matches()) {
            txtVehId.requestFocus();
            txtVehId.setFocusColor(Paint.valueOf("Red"));
            lblId.setText("Invalid Vehicle Id!");
        }
    }
    public void txtVehPriceOnKetReleased(KeyEvent keyEvent) {
        lblPrice.setText("");
        txtVehPrice.setFocusColor(Paint.valueOf("Blue"));

        Pattern pricePattern = Pattern.compile("^[0-9]{6,}$");
        vehPriceMatcher = pricePattern.matcher(txtVehPrice.getText());

        if(!vehPriceMatcher.matches()) {
            txtVehPrice.requestFocus();
            txtVehPrice.setFocusColor(Paint.valueOf("Red"));
            lblPrice.setText("Invalid Price");
        }

    }

    public void txtVehDescriptionOnKetReleased(KeyEvent keyEvent) {
        lblDesc.setText("");
        txtVehDescription.setFocusColor(Paint.valueOf("Blue"));

        Pattern descPattern = Pattern.compile("^[a-zA-Z0-9]{2,}$");
        vehDescriptionMatcher = descPattern.matcher(txtVehDescription.getText());

        if(!vehDescriptionMatcher.matches()) {
            txtVehDescription.requestFocus();
            txtVehDescription.setFocusColor(Paint.valueOf("Red"));
            lblDesc.setText("Invalid Data!");
        }
    }

    public void txtVehBrandOnKetReleased(KeyEvent keyEvent) {
        lblBrand.setText("");
        txtVehBrand.setFocusColor(Paint.valueOf("Blue"));

        Pattern brandPattern = Pattern.compile("^[a-zA-Z0-9]{2,}$");
        vehBrandMatcher = brandPattern.matcher(txtVehBrand.getText());

        if(!vehBrandMatcher.matches()) {
            txtVehBrand.requestFocus();
            txtVehBrand.setFocusColor(Paint.valueOf("Red"));
            lblBrand.setText("Invalid Brand Name!");
        }
    }

    public void txtVehFuelOnKetReleased(KeyEvent keyEvent) {
        lblFuel.setText("");
        txtVehFuel.setFocusColor(Paint.valueOf("Blue"));

        Pattern fuelPattern = Pattern.compile("^(petrol|Petrol|PETROL|diesel|Diesel|DIESEL)$");
        vehFuelMatcher = fuelPattern.matcher(txtVehFuel.getText());

        if (!vehFuelMatcher.matches()) {
            txtVehFuel.requestFocus();
            txtVehFuel.setFocusColor(Paint.valueOf("Red"));
            lblFuel.setText("Invalid Fuel Type!");

        }
    }

    public void txtVehStockIdOnKetReleased(KeyEvent keyEvent) {
        lblStockId.setText("");
        txtStockId.setFocusColor(Paint.valueOf("Blue"));

        Pattern fuelPattern = Pattern.compile("^(S|s)([000-999]){3}$");
        vehStockIdMatcher = fuelPattern.matcher(txtStockId.getText());

        if (!vehStockIdMatcher.matches()) {
            txtStockId.requestFocus();
            txtStockId.setFocusColor(Paint.valueOf("Red"));
            lblStockId.setText("Invalid Stock Id!");

        }
    }
    public void txtVehQtyOnKeyReleased(KeyEvent keyEvent) {
        lblQty.setText("");
        txtVehQty.setFocusColor(Paint.valueOf("Blue"));

        Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");
        vehQtyMatcher = qtyPattern.matcher(txtVehQty.getText());

        if (!vehQtyMatcher.matches()) {
            txtVehQty.requestFocus();
            txtVehQty.setFocusColor(Paint.valueOf("Red"));
            lblQty.setText("Invalid Qty!");

        }
    }

    public void txtVehIdOnAction(ActionEvent actionEvent) {
        txtVehPrice.requestFocus();
    }

    public void txtVehPriceOnAction(ActionEvent actionEvent) {
        txtVehDescription.requestFocus();
    }

    public void txtVehDescriptionOnAction(ActionEvent actionEvent) {
        txtVehBrand.requestFocus();
    }

    public void txtVehBrandOnAction(ActionEvent actionEvent) {
        txtVehFuel.requestFocus();
    }

    public void txtVehFuelOnAction(ActionEvent actionEvent) {
        txtStockId.requestFocus();
    }

    public void txtStockIdOnAction(ActionEvent actionEvent) {
        txtVehQty.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colVehId.setCellValueFactory(new PropertyValueFactory<>("vehId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colFuel.setCellValueFactory(new PropertyValueFactory<>("fuel"));
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        setTableData();
    }

    private void setTableData() {
        tblVehicle.getItems().clear();
        try {
            ResultSet rst = VehicleModel.getAllData();
            while (rst.next()) {
                tm.add(new VehicleTM(
                        rst.getString(1),
                        rst.getDouble(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getInt(7)

                ));
            }
            tblVehicle.setItems(tm);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
