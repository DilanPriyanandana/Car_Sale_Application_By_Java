package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import lk.ijse.dilanCarSale.model.EmployeeModel;
import lk.ijse.dilanCarSale.model.StockModel;
import lk.ijse.dilanCarSale.model.SupplierModel;
import lk.ijse.dilanCarSale.tm.PlaceOrderTM;
import lk.ijse.dilanCarSale.tm.StockTM;
import lk.ijse.dilanCarSale.to.Stock;
import lk.ijse.dilanCarSale.to.Supplier;
import lk.ijse.dilanCarSale.util.Navigation;
import lk.ijse.dilanCarSale.util.Routes;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import rex.utils.S;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockFormController implements Initializable {
    public AnchorPane paneStock;
    public JFXComboBox cmbSupId;
    public JFXDatePicker dateStock;
    public TextField txtSupId;
    public TextField txtDate;
    public JFXButton btnviewReport;
    public Label lblStockId;
    public Label lblQty;
    public Label lblDesc;
    public Matcher qtyMatcher;
    public Matcher stockIdMatcher;
    public Matcher priceMatcher;
    public JFXTextField txtStockPrice;
    public Label lblPrice;
    @FXML
    private TableView<StockTM> tblStock;
    public TableColumn colStockId;
    public TableColumn colQty;
    public TableColumn colVehPrice;
    public TableColumn colVehDesc;
    public TableColumn colDate;
    public TableColumn colSupId;
    public JFXButton btnAdd;
    public JFXButton btnSearch;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public ObservableList<StockTM> tm = FXCollections.observableArrayList();
    @FXML
    private JFXTextField txtStockId;

    @FXML
    private JFXTextField txtStockQty;

    @FXML
    private JFXTextField txtStockDescription;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getSupplierIds();
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colVehPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colVehDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        setTableData();
    }

    private void setTableData() {
        tblStock.getItems().clear();
        try{
            ResultSet set = StockModel.getAllData();
                while (set.next()) {
                   tm.add(new StockTM(
                            set.getString(1),
                            set.getInt(2),
                            set.getDouble(3),
                            set.getString(4),
                            set.getDate(5).toLocalDate(),
                            set.getString(6)

                    ));
                }
            tblStock.setItems(tm);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
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


    public void btnviewReportOnAction(ActionEvent actionEvent) {
        InputStream resource = getClass().getResourceAsStream("/lk/ijse/dilanCarSale/report/StockDetails.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtStockIdOnKeyReleased(KeyEvent keyEvent) {
        lblStockId.setText("");
        txtStockId.setFocusColor(Paint.valueOf("Blue"));

        Pattern userNamePattern = Pattern.compile("^(S)([000-999]){3}$");
        stockIdMatcher = userNamePattern.matcher(txtStockId.getText());

        if (!stockIdMatcher.matches()) {
            txtStockId.requestFocus();
            txtStockId.setFocusColor(Paint.valueOf("Red"));
            lblStockId.setText("Invalid Id!");
        }
    }

    public void txtStockQtyOnKeyReleased(KeyEvent keyEvent) {
        lblQty.setText("");
        txtStockQty.setFocusColor(Paint.valueOf("Blue"));

        Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");
        qtyMatcher = qtyPattern.matcher(txtStockQty.getText());

        if (!qtyMatcher.matches()) {
            txtStockQty.requestFocus();
            txtStockQty.setFocusColor(Paint.valueOf("Red"));
            lblQty.setText("Invalid Qty!");
        }
    }
    public void txtStockPriceOnKeyReleased(KeyEvent keyEvent) {
        lblPrice.setText("");
        txtStockPrice.setFocusColor(Paint.valueOf("Blue"));

        Pattern qtyPattern = Pattern.compile("^[0-9]{5,}$");
        priceMatcher = qtyPattern.matcher(txtStockPrice.getText());

        if (!priceMatcher.matches()) {
            txtStockPrice.requestFocus();
            txtStockPrice.setFocusColor(Paint.valueOf("Red"));
            lblPrice.setText("Invalid Price!");
        }}

    public void btnAddOnAction(ActionEvent actionEvent) {
        try{
        String stockId = txtStockId.getText();
        int qty = Integer.parseInt(txtStockQty.getText());
        double price= Double.parseDouble(txtStockPrice.getText());
        String description = txtStockDescription.getText();
        LocalDate date =dateStock.getValue();
        String supId = String.valueOf(cmbSupId.getValue());

        Stock stock=new Stock(stockId, qty,price,description,date,supId);

        try{

            boolean isAdded = StockModel.addStock(stock);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Stock Added Successfully!").show();
                setTableData();
            }else {
                new Alert(Alert.AlertType.ERROR, "Stock Didn't Added!").show();
            }

        }catch (ClassNotFoundException | SQLException ex){
            new Alert(Alert.AlertType.ERROR, "Please Input Correct Data!").show();
        }}catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }
    public void btnSearchOnAction(ActionEvent actionEvent) {
        String stockId = txtStockId.getText();
        try {
            Stock stock= StockModel.searchStock(stockId);

            if (stock != null) {
                txtStockQty.setText(String.valueOf(stock.getQty()));
                txtStockPrice.setText(String.valueOf(stock.getPrice()));
                txtStockDescription.setText(stock.getDescription());
                dateStock.setValue(stock.getDate());
                cmbSupId.setValue(stock.getSupId());
            }else{
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try{
        String stockId = txtStockId.getText();
        int qty = Integer.parseInt(txtStockQty.getText());
        double price= Double.parseDouble(txtStockPrice.getText());
        String description = txtStockDescription.getText();
        LocalDate date =dateStock.getValue();
        String supId = String.valueOf(cmbSupId.getValue());

        Stock stock=new Stock(stockId, qty,price,description,date,supId);

        try{
            boolean isAdded = StockModel.updateStock(stock);
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION, "Stock UpdatedSuccessfully!").show();
                setTableData();
            }else {
                new Alert(Alert.AlertType.WARNING, "Didn't Updated!").show();
            }

        }catch (ClassNotFoundException | SQLException ex){
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String stockId = txtStockId.getText();

        try {
            boolean isDeleted= StockModel.deleteStock(stockId);

            if (isDeleted) {
                new Alert(Alert.AlertType.WARNING, "Deleted Successfully!").show();
                setTableData();
            }else{
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Id!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!").show();
        }
    }

    public void txtStockIdOnAction(ActionEvent actionEvent) {
        txtStockQty.requestFocus();
    }

    public void txtStockQtyOnAction(ActionEvent actionEvent) {
        txtStockPrice.requestFocus();
    }

    public void txtStockDescriptionOnAction(ActionEvent actionEvent) {
        dateStock.requestFocus();
    }
    public void txtStockPriceOnAction(ActionEvent actionEvent) {
        txtStockDescription.requestFocus();
    }

}

