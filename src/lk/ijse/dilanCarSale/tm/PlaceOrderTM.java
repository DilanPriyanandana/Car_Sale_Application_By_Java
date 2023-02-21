package lk.ijse.dilanCarSale.tm;

import javafx.scene.control.Button;

public class PlaceOrderTM {
    private String vehId;
    private String Brand;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btnDelete;

    public PlaceOrderTM() {
    }

    public PlaceOrderTM(String vehId, String brand, String description, int qty, double unitPrice, double total, Button btnDelete) {
        this.vehId = vehId;
        Brand = brand;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
        this.btnDelete = btnDelete;
    }

    public String getVehId() {
        return vehId;
    }

    public void setVehId(String vehId) {
        this.vehId = vehId;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "PlaceOrderTM{" +
                "vehId='" + vehId + '\'' +
                ", Brand='" + Brand + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", btnDelete=" + btnDelete +
                '}';
    }
}

