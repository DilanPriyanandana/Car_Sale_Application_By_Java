package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.model.*;
import lk.ijse.dilanCarSale.tm.PlaceOrderTM;
import lk.ijse.dilanCarSale.tm.StockTM;
import lk.ijse.dilanCarSale.to.CartDetails;
import lk.ijse.dilanCarSale.to.Customer;
import lk.ijse.dilanCarSale.to.PlaceOrder;
import lk.ijse.dilanCarSale.to.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblFuel;
    public JFXButton btnPrintBill;
    public JFXButton txtHistory;
    @FXML
    private TableView<PlaceOrderTM> tblOrder;
    public TableColumn clmVehicleId;
    public Label lblOrderId;
    public Label lblCustomerName;
    public JFXComboBox cmbCusId;
    public JFXComboBox cmbVehId;
    public TextField txtQtyVehicle;
    public JFXButton txtAddCart;
    public JFXButton btnPlaceOrder;
    public AnchorPane panePlaceOrder;
    public TableColumn clmBrand;
    public TableColumn clmDescription;
    public TableColumn clmQty;
    public TableColumn clmPrice;
    public TableColumn clmTotal;
    public TableColumn clmAction;
    public Label lblDate;
    public Label lblTime;
    public Label lblVehBrand;
    public Label lblVehDescription;
    public Label lblVehUnitPrice;

    public ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    public Label lblVehStockId;
    public Label lblQtyOnHand;

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try{
        String orderId = lblOrderId.getText();
        String customerId = String.valueOf(cmbCusId.getValue());
        String brand=lblVehBrand.getText();
        String fuel=lblFuel.getText();
        String stockId=lblVehStockId.getText();

        ArrayList<CartDetails> cartDetails = new ArrayList<>();

        /* load all cart items' to orderDetails arrayList */
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            /* get each row details to (PlaceOrderTm)tm in each time and add them to the orderDetails */
            PlaceOrderTM tm = obList.get(i);
            cartDetails.add(new CartDetails(orderId, tm.getVehId(), tm.getQty(), tm.getDescription(), tm.getUnitPrice(),brand,fuel,stockId,tm.getTotal()));
        }

        PlaceOrder placeOrder = new PlaceOrder(customerId, orderId, cartDetails);
        try {
            boolean isPlaced = PlaceOrderModel.placeOrder(placeOrder);
            if (isPlaced) {
                /* to clear table */
                obList.clear();
                loadNextOrderId();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }}catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void txtAddCartOnAction(ActionEvent actionEvent) {
        try{
       String vehId= String.valueOf(cmbVehId.getValue());
       String Brand=lblVehBrand.getText();
       double unitPrice= Double.parseDouble(lblVehUnitPrice.getText());
       String description=lblVehDescription.getText();
       int qty= Integer.parseInt(txtQtyVehicle.getText());
       double total=unitPrice * qty;
       Button btnDelete=new Button("Delete");

       txtQtyVehicle.setText("");

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (clmVehicleId.getCellData(i).equals(cmbVehId)) {
                    qty += (int) clmQty.getCellData(i);
                    total = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);
                    tblOrder.refresh();
                    return;
                }
            }
        }

        btnDelete.setOnAction((e) -> {
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == ok) {
                PlaceOrderTM tm =tblOrder.getSelectionModel().getSelectedItem();
                tblOrder.refresh();
            }else {
                tblOrder.getItems().removeAll(tblOrder.getSelectionModel().getSelectedItem());
            }
        });
        obList.add(new PlaceOrderTM(vehId, Brand, description, qty, unitPrice, total, btnDelete));
        tblOrder.setItems(obList);
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLocalDate();
        setLocalTime();
        loadCusIds();
        loadVehIds();
        loadNextOrderId();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        clmVehicleId.setCellValueFactory(new PropertyValueFactory("vehId"));
        clmBrand.setCellValueFactory(new PropertyValueFactory("brand"));
        clmDescription.setCellValueFactory(new PropertyValueFactory("description"));
        clmQty.setCellValueFactory(new PropertyValueFactory("qty"));
        clmPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        clmTotal.setCellValueFactory(new PropertyValueFactory("total"));
        clmAction.setCellValueFactory(new PropertyValueFactory("btnDelete"));
        refreshTable();
    }

    private void refreshTable() {
        /*tblOrder.getItems().clear();
        try{
            ResultSet set = OrderModel.getAllData();
            while (set.next()) {
                obList.add(new PlaceOrderTM(
                        set.getString(1),
                        set.getInt(2),
                        set.getDouble(3),
                        set.getString(4),
                        set.getDate(5).toLocalDate(),
                        set.getString(6)

                ));
            }
            tblOrder.setItems(obList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }*/
    }

    private void loadNextOrderId() {
        try {
            String orderId = OrderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadVehIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> itemList = VehicleModel.loadVehicleIds();

            for (String code : itemList) {
                observableList.add(code);
            }
            cmbVehId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCusIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = CustomerModel.loadCustomerIds();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbCusId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setLocalTime() {
        lblTime.setText(String.valueOf(LocalTime.now()));
    }

    private void setLocalDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));

    }

    public void cmbVehIdOnAction(ActionEvent actionEvent) {
        String vehId = String.valueOf(cmbVehId.getValue());
        try {
            Vehicle vehicle = VehicleModel.searchVehicle(vehId);
            fillVehicleFields(vehicle);
            txtQtyVehicle.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillVehicleFields(Vehicle vehicle) {
        lblVehBrand.setText(vehicle.getBrand());
        lblVehDescription.setText(vehicle.getDescription());
        lblVehStockId.setText(vehicle.getStockId());
        lblVehUnitPrice.setText(String.valueOf(vehicle.getPrice()));
        lblQtyOnHand.setText(String.valueOf(vehicle.getQty()));
        lblFuel.setText(vehicle.getFuel());

    }
    private void fillCustomerField(Customer customer) {
        lblCustomerName.setText(customer.getName());
    }

    public void cmbCusIdOnAction(ActionEvent actionEvent) {
        String cusId= String.valueOf(cmbCusId.getValue());
        try {
            Customer customer=CustomerModel.searchCustomer(cusId);
            fillCustomerField(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/CustomerBill.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtHistoryOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/OrderDetail.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
