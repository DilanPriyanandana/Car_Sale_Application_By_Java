package lk.ijse.dilanCarSale.model;

import lk.ijse.dilanCarSale.to.Employee;
import lk.ijse.dilanCarSale.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static boolean saveEmployee(Employee employee) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, employee.getId(), employee.getName(), employee
                .getAddress(), employee.getContact(), employee.getEmail(), employee.getNic());

    }

    public static ArrayList<String> loadEmployeeIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT employee_id FROM employee";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }
    public static int getEmployeeCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(employee_id) FROM employee";
        ResultSet result = CrudUtil.execute(sql);
        if(result.next()){
            return result.getInt(1);
        }
        return -1;
    }
}
