package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.to.EmployeePayment;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.SQLException;

public class EmployeePaymentModel {
    public static boolean savePayment(EmployeePayment ep) throws SQLException, ClassNotFoundException {
            String sql="INSERT INTO employee_payment_details VALUES (?,?,?,?,?)";
            return CrudUtil.execute(sql, ep.getEmpId(),ep.getDescription(),ep.getAmount(), ep.getDate(), ep.getTime());
    }

}
