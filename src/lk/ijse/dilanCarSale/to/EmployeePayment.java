package lk.ijse.dilanCarSale.to;

import java.time.LocalDate;
import java.time.LocalTime;

public class EmployeePayment {
    private String empId;
    private String description;
    private double amount;
    private LocalDate date;
    private LocalTime time;

    public EmployeePayment() {
    }

    public EmployeePayment(String empId, String description, double amount, LocalDate date, LocalTime time) {

        this.empId = empId;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String supId) {
        this.empId= supId;
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
        return "EmployeePayment{" +
                "supId='" + empId + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", time=" + time +
                '}';
    }


}
