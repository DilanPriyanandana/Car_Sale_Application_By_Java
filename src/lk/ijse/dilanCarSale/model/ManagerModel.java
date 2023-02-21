package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.to.Manager;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.SQLException;

public class ManagerModel {
    public static boolean saveManager(Manager manager) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO manager VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, manager.getId(), manager.getName(), manager
                .getAddress(), manager.getContact(), manager.getEmail(), manager.getNic());
    }
}
