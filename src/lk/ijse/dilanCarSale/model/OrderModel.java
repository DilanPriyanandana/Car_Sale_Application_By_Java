package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.to.Order;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {

    public static boolean save(Order order) throws SQLException, ClassNotFoundException {
        System.out.println(order.getOrderId());
        String sql = "INSERT INTO Orders VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql, order.getOrderId(), order.getDate(), order.getTime(), order.getCusId());
    }
    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT order_id FROM Orders ORDER BY order_id DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return generateNextOrderId(result.getString(1));
        }
        return generateNextOrderId(result.getString(null));
    }

    private static String generateNextOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O0");
            int id = Integer.parseInt(split[1]);
            id += 1;
            return "O0" + id;
        }
        return "OD001";

    }

    public static ResultSet getAllData() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM OrderDetail");
    }
}
