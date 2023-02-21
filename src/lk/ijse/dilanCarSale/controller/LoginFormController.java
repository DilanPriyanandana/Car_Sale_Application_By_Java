package lk.ijse.dilanCarSale.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.dilanCarSale.util.Navigation;
import lk.ijse.dilanCarSale.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFormController{
    public AnchorPane paneTask;
    public JFXButton btnLogin;
    public JFXButton btnSignUp;
    public JFXButton btnClose;
    public AnchorPane pane;
    public Matcher userNameMatcher;
    public Matcher passwordMatcher;
    public Label lblUserNameWarning;
    public Label lblPasswordWarning;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        if(txtUserName.getText().equals("manager") && txtPassword.getText().equals("123")){
            Navigation.navigate(Routes.MANAGER,paneTask);

        }else if(txtUserName.getText().equals("employee") && txtPassword.getText().equals("123")){
            Navigation.navigate(Routes.EMPLOYEE,paneTask);
        }
        else{
            new Alert(Alert.AlertType.ERROR, "Input Correct User Name And Password!").show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
       }

    }
    public void txtUserNameKeyTypedOnAction(KeyEvent keyEvent) {
        txtUserName.setFocusColor(Paint.valueOf("Blue"));
        lblUserNameWarning.setText("");

        Pattern userNamePattern = Pattern.compile("^(manager|employee)$");
        userNameMatcher = userNamePattern.matcher(txtUserName.getText());

        if(!userNameMatcher.matches()) {
            txtUserName.requestFocus();
            txtUserName.setFocusColor(Paint.valueOf("Red"));
        }
    }
    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SIGN_UP,paneTask);
    }
    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
    public void txtPasswordOnAction(ActionEvent actionEvent) {
        btnLogin.fire();
    }
    public void btnCloseOnAction(MouseEvent mouseEvent) {
        System.exit(1);
    }


    public void txtPasswordKeyTypedOnAction(KeyEvent keyEvent) {
      //  lblPasswordWarning.setText("");
        txtPassword.setFocusColor(Paint.valueOf("Blue"));

        Pattern userNamePattern = Pattern.compile("^(123)$");
        passwordMatcher = userNamePattern.matcher(txtPassword.getText());

        if(!passwordMatcher.matches()) {
            txtPassword.requestFocus();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
           // lblPasswordWarning.setText("Please enter Password correctly !");
        }
    }
}
