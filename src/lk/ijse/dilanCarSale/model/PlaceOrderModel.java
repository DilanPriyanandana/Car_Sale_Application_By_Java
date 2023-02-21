package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.to.Order;
import lk.ijse.dilanCarSale.to.PlaceOrder;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class PlaceOrderModel {
    public static boolean placeOrder(PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            boolean isOrderAdded = OrderModel.save(new Order(placeOrder.getOrderId(), LocalDate.now(), LocalTime.now(), placeOrder.getCustomerId()));
            if (isOrderAdded) {
                boolean isUpdated = VehicleModel.updateQty(placeOrder.getOrderDetails());
                if (isUpdated) {
                    boolean isOrderDetailAdded = OrderDetailModel.saveOrderDetails(placeOrder.getOrderDetails());
                    if (isOrderDetailAdded) {
                        DBConnection.getInstance().getConnection().commit();
                        return true;
                    }
                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;
        } finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }
}
