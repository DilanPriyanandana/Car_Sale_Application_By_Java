package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.dilanCarSale.model.EmployeeModel;
import lk.ijse.dilanCarSale.model.ManagerModel;
import lk.ijse.dilanCarSale.to.Employee;
import lk.ijse.dilanCarSale.to.Manager;
import lk.ijse.dilanCarSale.util.Navigation;
import lk.ijse.dilanCarSale.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFormController {
    public AnchorPane paneSignUp;
    public JFXButton btnSignUp;
    public JFXButton btnClose;
    public JFXButton btnBack;
    public JFXCheckBox checkManager;
    public JFXCheckBox checkEmployee;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtNic;

    public Matcher idMatcher;
    public Matcher nameMatcher;
    public Matcher addressMatcher;
    public Matcher contactMatcher;
    public Matcher emailMatcher;
    public Matcher nicMatcher;

    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException {
        try{
        String id=txtId.getText();
        String name=txtName.getText();
        String address=txtAddress.getText();
        int contact= Integer.parseInt(txtContact.getText());
        String email=txtEmail.getText();
        String nic=txtNic.getText();

        if(checkManager.isSelected()){
            Manager manager =new Manager(id, name, address, contact, email, nic);

            try {
                boolean isAdded = ManagerModel.saveManager(manager);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "You Have Created Your Manager Account Successfully!\nYour Username - manager\nYour Password - 123").show();
                    Navigation.navigate(Routes.LOGIN,paneSignUp);
                } else {
                    new Alert(Alert.AlertType.WARNING, "OOps!Try again!").show();
                }
            }catch (SQLException | ClassNotFoundException ex){
                new Alert(Alert.AlertType.WARNING, "Please Input Correct Data!").show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else if(checkEmployee.isSelected()){
            Employee employee = new Employee (id, name, address, contact, email, nic);
            try {
                boolean isAdded = EmployeeModel.saveEmployee(employee);

                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "You Have Created Your Employee Account Successfully!\nYour Username - employee\nYour Password - 123").show();
                    Navigation.navigate(Routes.LOGIN,paneSignUp);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }catch ( ClassNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please Select Role").show();
        }
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "Please Input All Data!").show();
        }
    }

    public void btnBackOnAction(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.AFTER_LOGIN,paneSignUp);
    }

    public void btnCloseOnAction(MouseEvent mouseEvent) {
        System.exit(3);
    }

    public void checkManagerOnAction(ActionEvent actionEvent) {
        if(checkManager.isSelected()){
            checkEmployee.setSelected(false);
        }
    }
    public void checkEmployeeOnAction(ActionEvent actionEvent) {
        if (checkEmployee.isSelected()) {
            checkManager.setSelected(false);
        }
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void txtAddressOnAction(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void txtNicOnAction(ActionEvent actionEvent) {
        checkEmployee.requestFocus();
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
        txtNic.requestFocus();
    }

    public void txtContactOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void txtIdKeyTypedOnAction(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(MAN|EMP)([000-999]){3}$");
        idMatcher = idPattern.matcher(txtId.getText());

        txtId.setFocusColor(Paint.valueOf("Blue"));
        if(!idMatcher.matches()) {
            txtId.requestFocus();
            txtId.setFocusColor(Paint.valueOf("Red"));
        }
    }

    public void txtNameKeyTypedOnAction(KeyEvent keyEvent) {
        Pattern namePattern = Pattern.compile("^[a-zA-Z0-9]{4,}$");
        nameMatcher = namePattern.matcher(txtName.getText());

        txtName.setFocusColor(Paint.valueOf("Blue"));
        if(!nameMatcher.matches()) {
            txtName.requestFocus();
            txtName.setFocusColor(Paint.valueOf("Red"));
        }
    }

    public void txtAddressKeyTypedOnAction(KeyEvent keyEvent) {
        Pattern addressPattern = Pattern.compile("^[0-9a-zA-Z]{2,}.$");
        addressMatcher = addressPattern.matcher(txtAddress.getText());

        txtAddress.setFocusColor(Paint.valueOf("Blue"));
        if(!addressMatcher.matches()) {
            txtAddress.requestFocus();
            txtAddress.setFocusColor(Paint.valueOf("Red"));
        }

    }

    public void txtContactKeyTypedOnAction(KeyEvent keyEvent) {
        Pattern contactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$");
        contactMatcher = contactPattern.matcher(txtContact.getText());

        txtContact.setFocusColor(Paint.valueOf("Blue"));
        if(!contactMatcher.matches()) {
            txtContact.requestFocus();
            txtContact.setFocusColor(Paint.valueOf("Red"));
        }
    }

    public void txtEmailKeyTypedOnAction(KeyEvent keyEvent) {
        Pattern emailPattern = Pattern.compile("^([a-z0-9]{2,})([@])([a-z]{2,9})([.])([a-z]{2,})$");
        emailMatcher = emailPattern.matcher(txtEmail.getText());

        txtEmail.setFocusColor(Paint.valueOf("Blue"));
        if(!emailMatcher.matches()) {
            txtEmail.requestFocus();
            txtEmail.setFocusColor(Paint.valueOf("Red"));
        }
    }

    public void txtNicKeyTypedOnAction(KeyEvent keyEvent) {
        Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}(v)$");
        nicMatcher = nicPattern.matcher(txtNic.getText());

        txtNic.setFocusColor(Paint.valueOf("Blue"));
        if(!nicMatcher.matches()) {
            txtNic.requestFocus();
            txtNic.setFocusColor(Paint.valueOf("Red"));
        }
    }
}

