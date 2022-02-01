package com.javafinal.kheladi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button button_login;
    @FXML
    private  Button button_sign_up;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;


    //Mouse enter and mouse exit
    @FXML
    private void mouseEnter (MouseEvent event){
        ((Button) event.getSource()).setStyle("-fx-background-color: #0055da;");

    }

    @FXML
    private void mouseExit(MouseEvent event){
        ((Button) event.getSource()).setStyle("-fx-background-color: #1d0024;");

    }


   @Override
    public void initialize(URL location, ResourceBundle resources){
 button_login.setOnAction(new EventHandler<ActionEvent>() {
     @Override
     public void handle(ActionEvent actionEvent) {
         System.out.println("Login Button Clicked!");
         DBUtils.logInUser(actionEvent, tf_username.getText(), tf_password.getText());
//         DBUtils.getStocks();
//         DBUtils.getMyStocks(tf_username.getText());
     }
 });



 button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
     @Override
     public void handle(ActionEvent actionEvent) {
         DBUtils.changeScene(actionEvent,"sign-up.fxml","Signup!", null, null, null,null);
     }
 });

   }

}