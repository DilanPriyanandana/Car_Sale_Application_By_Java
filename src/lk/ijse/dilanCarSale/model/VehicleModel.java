package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.db.DBConnection;
import lk.ijse.dilanCarSale.to.CartDetails;
import lk.ijse.dilanCarSale.to.Supplier;
import lk.ijse.dilanCarSale.to.Vehicle;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VehicleModel {

    public static boolean saveVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        System.out.println(vehicle.getStockId()+"in model");
        String sql = "INSERT INTO Vehicle VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql, vehicle.getVehID(), vehicle.getPrice(), vehicle.getDescription(), vehicle.getBrand(),
                vehicle.getFuel(), vehicle.getStockId(), vehicle.getQty());

    }

    public static Vehicle searchVehicle(String vehId) throws SQLException, ClassNotFoundException {
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet result = stm.executeQuery("SELECT  * FROM vehicle WHERE vehicle_id ='" + vehId + "'");

        if (result.next()) {
            return new Vehicle(
                    result.getString("vehicle_id"),
                    result.getDouble("vehicle_price"),
                    result.getString("vehicle_description"),
                    result.getString("vehicle_brand"),
                    result.getString("fuel_type"),
                    result.getString("stock_id"),
                    result.getInt("vehicle_qty")
            );
        }
        return null;
    }

    public static boolean deleteVehicle(String vehId) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().
                executeUpdate("DELETE FROM vehicle WHERE vehicle_id='" + vehId + "'") > 0;

    }

    public static ArrayList<String> loadVehicleIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT vehicle_id FROM vehicle";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static boolean updateVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE vehicle SET vehicle_price =?, vehicle_description=?, vehicle_brand=?, fuel_type=?, stock_id=?, vehicle_qty=? WHERE vehicle_id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, vehicle.getPrice());
        pstm.setObject(2, vehicle.getDescription());
        pstm.setObject(3, vehicle.getBrand());
        pstm.setObject(4, vehicle.getFuel());
        pstm.setObject(5, vehicle.getStockId());
        pstm.setObject(6, vehicle.getQty());
        pstm.setObject(7, vehicle.getVehID());

        return pstm.executeUpdate() > 0;
    }

    public static ResultSet getAllData() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM vehicle");
    }

    public static int getVehicleCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(vehicle_qty) FROM Vehicle";
        ResultSet result = CrudUtil.execute(sql);
        if (result.next()) {
            return result.getInt(1);
        }
        return -1;
    }

    public static boolean updateQty(ArrayList<CartDetails> cartDetail) throws SQLException, ClassNotFoundException {
        for (CartDetails cartDetails : cartDetail) {
            if (!updateQty(new Vehicle(cartDetails.getVehicleId(), cartDetails.getUnitPrice(), cartDetails.getDescription(), cartDetails.getBrand(),cartDetails.getFuel(), cartDetails.getStockId(), cartDetails.getQty()))) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Vehicle SET vehicle_qty = vehicle_qty - ? WHERE vehicle_id = ?";
        return CrudUtil.execute(sql, vehicle.getQty(), vehicle.getVehID());
    }
}

