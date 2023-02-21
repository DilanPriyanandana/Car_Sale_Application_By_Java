package lk.ijse.dilanCarSale.tm;

public class VehicleTM {
    private String vehId;
    private double price;
    private String desc;
    private String brand;
    private String fuel;
    private String stockId;
    private int qty;

    public VehicleTM() {
    }

    public VehicleTM(String vehId, double price, String desc, String brand, String fuel, String stockId, int qty) {
        this.vehId = vehId;
        this.price = price;
        this.desc = desc;
        this.brand = brand;
        this.fuel = fuel;
        this.stockId = stockId;
        this.qty=qty;
    }

    public String getVehId() {
        return vehId;
    }

    public void setVehId(String vehId) {
        this.vehId = vehId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "VehicleTM{" +
                "vehId='" + vehId + '\'' +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                ", brand='" + brand + '\'' +
                ", fuel='" + fuel + '\'' +
                ", stockId='" + stockId + '\'' +
                ", qty=" + qty +
                '}';
    }
}
