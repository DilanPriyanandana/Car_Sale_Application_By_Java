package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.to.Stock;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockModel {
    public static boolean addStock(Stock stock) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO Stock VALUES (?,?,?,?,?,?)";
            return CrudUtil.execute(sql, stock.getStockId(), stock.getQty(), stock.getPrice(), stock.getDescription(), stock.getDate(), stock.getSupId());
    }

    public static Stock searchStock(String stockId) throws SQLException, ClassNotFoundException {
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet result = stm.executeQuery("SELECT  * FROM Stock WHERE stock_id ='"+stockId+"'");

        if (result.next()) {
            return new Stock(
                    result.getString("stock_id"),
                    result.getInt("stock_qty"),
                    result.getInt("price"),
                    result.getString("stock_description"),
                    result.getDate("date").toLocalDate(),
                    result.getString("supplier_id")
            );
        }
        return null;
    }

    public static boolean updateStock(Stock stock) throws SQLException, ClassNotFoundException {
        String sql="UPDATE Stock SET stock_qty =?, price=?, stock_description=?, date=?, supplier_id=? WHERE stock_id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,stock.getQty());
        pstm.setObject(2,stock.getPrice());
        pstm.setObject(3,stock.getDescription());
        pstm.setObject(4,stock.getDate());
        pstm.setObject(5,stock.getSupId());
        pstm.setObject(6,stock.getStockId());

        return pstm.executeUpdate()>0;
    }

    public static boolean deleteStock(String stockId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Stock WHERE stock_id='"+stockId+"'")>0;

    }

    public static ResultSet getAllData() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM Stock");
    }
}
