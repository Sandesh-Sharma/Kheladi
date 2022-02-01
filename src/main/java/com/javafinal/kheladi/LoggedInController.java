package com.javafinal.kheladi;
//imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    //panes and different sections of application
    @FXML private Pane pn_compare_stocks,pn_stock_news,pn_sectorwise;
    @FXML private GridPane pn_my_stocks;
    @FXML private WebView myWebView;
    private WebEngine e;
    //UI buttons and labels
    @FXML private Button button_logout;
    @FXML private Label label_welcome, label_kheladi_type,label_location,label_category;
    //main side buttons
    @FXML private Button btn_nepse_live,btn_sectorwise,btn_my_stocks,btn_compare_stocks,btn_stock_news;
    //tableview
    @FXML private TableView stocks_table;
    @FXML private TableColumn tbl_clm_name,tbl_clm_symbol,tbl_clm_ltp,tbl_clm_sector;


    @Override
    public  void initialize(URL location, ResourceBundle resources){
        // intial Implementations
        e= myWebView.getEngine();
        e.load("https://www.nepsealpha.com/trading/chart");
        tbl_clm_symbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        tbl_clm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_clm_ltp.setCellValueFactory(new PropertyValueFactory<>("ltp"));
        tbl_clm_sector.setCellValueFactory(new PropertyValueFactory<>("sector"));


    // Logout button Action listner
    button_logout.setOnAction(new EventHandler<ActionEvent>() {
                                      @Override
                                      public void handle(ActionEvent actionEvent) {
                                          DBUtils.changeScene(actionEvent,"login-view.fxml","Login", null, null,null,null);

                                      }});}


        // handle clicks of category items
    @FXML
    private void handleClicks (ActionEvent event){
        if(event.getSource()==btn_nepse_live){
            label_location.setText("/home/nepse_live");
            label_category.setText("NEPSE LIVE");
            myWebView.toFront();
        }
        else if(event.getSource()==btn_my_stocks){
            label_location.setText("/home/my_stocks");
            label_category.setText("MY STOCKS");
            pn_my_stocks.toFront();
        }
        else if(event.getSource()==btn_compare_stocks){
            label_location.setText("/home/compare_stocks");
            label_category.setText("COMPARE STOCKS");
            pn_compare_stocks.toFront();
        }
        else if(event.getSource()==btn_stock_news){
            label_location.setText("/home/stock_news");
            label_category.setText("STOCK_NEWS");
            pn_stock_news.toFront();
        }
        else if(event.getSource()==btn_sectorwise){
            label_location.setText("/home/sectorwise");
            label_category.setText("SECTORWISE");
            pn_sectorwise.toFront();
        }
}

     //mouse drag and mouse drag over for buttons
    @FXML private void mouseEnter (MouseEvent event){
         ((Button) event.getSource()).setStyle("-fx-background-color: #0055da;");

    }
    @FXML private void mouseExit(MouseEvent event){
        ((Button) event.getSource()).setStyle("-fx-background-color: #1d0024;");

    }




   // Dashboard welcome and type label
    public void setUserInformation(String username, String type, ArrayList stocks){
        label_welcome.setText("Welcome" + username + "!" );
        label_kheladi_type.setText("A "+ type);
        for (int i=0; i<stocks.size();i++) {
        stocks_table.getItems().add(stocks.get(i));
        }


    }


}
