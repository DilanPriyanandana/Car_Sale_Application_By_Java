package lk.ijse.dilanCarSale.to;

import java.time.LocalDate;
import java.time.LocalTime;

public class SupplierPayment {
    private String supId;
    private String description;
    private double amount;
    private LocalDate date;
    private LocalTime time;

    public SupplierPayment() {
    }

    public SupplierPayment(String supId, String description, double amount, LocalDate date, LocalTime time) {

        this.supId = supId;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SupplierPayment{" +
                "supId='" + supId + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
