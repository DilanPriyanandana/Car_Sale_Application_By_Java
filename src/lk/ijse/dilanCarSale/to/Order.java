package lk.ijse.dilanCarSale.to;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {
    private String orderId;
    private LocalDate date;
    private LocalTime time;
    private String cusId;

    public Order() {
    }

    public Order(String orderId, LocalDate date, LocalTime time, String cusId) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.cusId = cusId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", cusId='" + cusId + '\'' +
                '}';
    }
}
