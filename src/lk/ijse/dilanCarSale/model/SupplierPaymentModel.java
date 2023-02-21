package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.to.SupplierPayment;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.SQLException;

public class SupplierPaymentModel {
    public static boolean savePayment(SupplierPayment sp) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO supplier_payment_details VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, sp.getSupId(),sp.getDescription(),sp.getAmount(), sp.getDate(), sp.getTime());
    }
}
