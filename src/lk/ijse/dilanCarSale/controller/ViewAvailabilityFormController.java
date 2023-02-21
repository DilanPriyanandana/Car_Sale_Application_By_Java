package lk.ijse.dilanCarSale.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.model.VehicleModel;
import lk.ijse.dilanCarSale.tm.VehicleTM;
import lk.ijse.dilanCarSale.to.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewAvailabilityFormController implements Initializable {
    public AnchorPane paneViewAvailability;
    public Button btnGetReport;
    public TableView tblAvailableVehicle;
    public TableColumn clmVehId;
    public TableColumn clmVehPrice;
    public TableColumn clmVehBrand;
    public TableColumn clmVehDescription;
    public TableColumn clmVehFuelType;
    public TableColumn clmStockId;

    public TableView<VehicleTM> table;
    public TableColumn clmQty;

    ObservableList<VehicleTM> tm = FXCollections.observableArrayList();

    public void btnGetReportOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/VehicleReport.jrxml");
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
        setTableData();
        clmVehId.setCellValueFactory(new PropertyValueFactory<>("vehId"));
        clmVehPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmVehBrand.setCellValueFactory(new PropertyValueFactory<>("desc"));
        clmVehDescription.setCellValueFactory(new PropertyValueFactory<>("brand"));
        clmVehFuelType.setCellValueFactory(new PropertyValueFactory<>("fuel"));
        clmStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }

    private void setTableData() {
        // tblAvailableVehicle.getItems().clear();
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
            tblAvailableVehicle.setItems(tm);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
