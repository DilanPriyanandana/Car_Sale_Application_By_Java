package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.to.CartDetails;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDetailModel {
    public static boolean saveOrderDetails(ArrayList<CartDetails> cartDetails) throws SQLException, ClassNotFoundException {
        for (CartDetails cartDetail : cartDetails) {
            if (!save(cartDetail)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(CartDetails cartDetail) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO OrderDetail VALUES(?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, cartDetail.getOrderId(), cartDetail.getVehicleId(), cartDetail.getQty(), cartDetail.getUnitPrice(), LocalDate.now(), LocalTime.now(),cartDetail.getTotal());
    }
}
