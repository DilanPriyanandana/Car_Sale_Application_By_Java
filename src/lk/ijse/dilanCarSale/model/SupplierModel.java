package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.to.Supplier;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SupplierModel {
    public static boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {

        String sql="INSERT INTO Supplier VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(sql,supplier.getSupId(),supplier.getSupName(),supplier.getSupAddress(),supplier.getSupContact(),
                supplier.getSupEmail(),supplier.getSupNic());

    }

    public static Supplier searchSupplier(String supId) throws SQLException, ClassNotFoundException {

        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet result = stm.executeQuery("SELECT  * FROM supplier WHERE supplier_id ='"+supId+"'");

        if (result.next()) {
            return new Supplier(
                    result.getString("supplier_id"),
                    result.getString("supplier_name"),
                    result.getString("supplier_address"),
                    result.getInt("supplier_contact"),
                    result.getString("supplier_email"),
                    result.getString("supplier_nic")
            );
        }
        return null;
    }

    public static boolean deleteSupplier(String supId) throws SQLException, ClassNotFoundException {

        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Supplier WHERE supplier_id='"+supId+"'")>0;

    }

    public static boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql="UPDATE supplier SET supplier_name =?, supplier_address=?, supplier_contact=?, supplier_email=?, supplier_nic=? WHERE supplier_id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,supplier.getSupName());
        pstm.setObject(2,supplier.getSupAddress());
        pstm.setObject(3,supplier.getSupContact());
        pstm.setObject(4,supplier.getSupEmail());
        pstm.setObject(5,supplier.getSupNic());
        pstm.setObject(6,supplier.getSupId());

        return pstm.executeUpdate()>0;


    }

    public static ArrayList<String> loadSupplierIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT supplier_id FROM supplier";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static ResultSet getAllData() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM Supplier");
    }
}
