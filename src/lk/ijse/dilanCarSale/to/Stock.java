package lk.ijse.dilanCarSale.to;

import java.time.LocalDate;

public class Stock {
    private String stockId;
    private int qty;
    private double price;
    private String description;
    private LocalDate date;
    private String supId;

    public Stock() {
    }

    public Stock(String stockId, int qty, double price, String description, LocalDate date, String supId) {
        this.stockId = stockId;
        this.qty = qty;
        this.price = price;
        this.description = description;
        this.date = date;
        this.supId = supId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId='" + stockId + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", supId='" + supId + '\'' +
                '}';
    }
}
