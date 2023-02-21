package lk.ijse.dilanCarSale.to;

public class Vehicle {
    private String vehID;
    private double price;
    private String description;
    private String brand;
    private String fuel;
    private String stockId;
    private int qty;

    public Vehicle() {
    }

    public Vehicle(String vehID, double price, String description, String brand, String fuel, String stockId, int qty) {
        this.vehID = vehID;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.fuel = fuel;
        this.stockId=stockId;
        this.qty=qty;
    }

    public String getVehID() {
        return vehID;
    }

    public void setVehID(String vehID) {
        this.vehID = vehID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setFuel(String stockId) {
        this.stockId = stockId;
    }
    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehID='" + vehID + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", fuel='" + fuel + '\'' +
                ", stockId='" + stockId + '\'' +
                ", qty=" + qty +
                '}';
    }
}
