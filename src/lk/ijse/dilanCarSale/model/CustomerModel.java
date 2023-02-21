package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.to.Customer;
import lk.ijse.dilanCarSale.to.Supplier;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerModel {
    public static boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, customer.getId(), customer.getName(), customer
                .getAddress(), customer.getContact(), customer.getEmail(), customer.getNic());
    }

    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String sql="UPDATE customer SET cus_name =?, cus_address=?, cus_contact=?, cus_email=?, cus_nic=? WHERE cus_id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,customer.getName());
        pstm.setObject(2,customer.getAddress());
        pstm.setObject(3,customer.getContact());
        pstm.setObject(4,customer.getEmail());
        pstm.setObject(5,customer.getNic());
        pstm.setObject(6,customer.getId());

        return pstm.executeUpdate()>0;
    }

    public static Customer searchCustomer(String cusId) throws SQLException, ClassNotFoundException {

        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet result = stm.executeQuery("SELECT  * FROM customer WHERE cus_id ='"+cusId+"'");

        if (result.next()) {
            return new Customer(
                    result.getString("cus_id"),
                    result.getString("cus_name"),
                    result.getString("cus_address"),
                    result.getInt("cus_contact"),
                    result.getString("cus_email"),
                    result.getString("cus_nic")
            );
        }
        return null;
    }

    public static boolean deleteCustomer(String cusId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM customer  WHERE cus_id='"+cusId+"'")>0;

    }

    public static ArrayList<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT cus_id FROM Customer";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static int getCustomerCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(cus_id) FROM customer";
        ResultSet result = CrudUtil.execute(sql);
        if(result.next()){
            return result.getInt(1);
        }
        return -1;
    }
}
