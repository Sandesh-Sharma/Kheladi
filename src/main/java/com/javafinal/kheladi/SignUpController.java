package com.javafinal.kheladi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button button_sign_up;

    @FXML
    private Button button_login;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private RadioButton rb_trader;

    @FXML RadioButton rb_investor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Now we have to group our radio button into a toggle group so that only one can be selected at a time.
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_investor.setToggleGroup(toggleGroup);
        rb_trader.setToggleGroup(toggleGroup);

        rb_investor.setSelected(true);


        //signup button event handling
        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Signup Button clicked!");
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()){
                    DBUtils.signUpUser(actionEvent,tf_username.getText(),tf_password.getText(),toggleName);
                }else
                {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to Sign up");
                    alert.show();

                }
            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent,"login-view.fxml","Login!",null, null );
            }
        });


    }
}
