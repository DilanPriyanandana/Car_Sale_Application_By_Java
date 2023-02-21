package lk.ijse.dilanCarSale.to;

public class CartDetails {
    private String orderId;
    private String vehicleId;
    private int qty;
    private String description;
    private double unitPrice;
    private String brand;
    private String fuel;
    private String stockId;
    private double total;

    public CartDetails() {
    }

    public CartDetails(String orderId, String vehicleId, int qty, String description, double unitPrice, String brand, String fuel, String stockId, double total) {
        this.orderId = orderId;
        this.vehicleId = vehicleId;
        this.qty = qty;
        this.description = description;
        this.unitPrice = unitPrice;
        this.brand = brand;
        this.fuel = fuel;
        this.stockId = stockId;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartDetails{" +
                "orderId='" + orderId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", qty=" + qty +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", brand='" + brand + '\'' +
                ", fuel='" + fuel + '\'' +
                ", stockId='" + stockId + '\'' +
                ", total=" + total +
                '}';
    }
}
