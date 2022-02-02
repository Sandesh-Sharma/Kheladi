package com.javafinal.kheladi;
//imports


import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import nepse.profitloss.ProfitLossCalculator;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    //Panes and different sections of application
    @FXML private Pane pn_compare_stocks,pn_stock_news,pn_sectorwise;
    @FXML private GridPane pn_my_stocks, pn_stocks;
    @FXML private WebView myWebView;
    private WebEngine e;
    //UI buttons and labels
    @FXML private Button button_logout;
    @FXML private Label label_welcome, label_kheladi_type,label_location,label_category;
    //Main-Side buttons
    @FXML private Button btn_nepse_live,btn_sectorwise,btn_my_stocks,btn_compare_stocks,btn_stock_news,btn_stocks;
    //Tableview
    @FXML private TableView stocks_table,stocks_table_my;
    @FXML private TableColumn tbl_clm_name,tbl_clm_symbol,tbl_clm_ltp,tbl_clm_sector,tbl_clm_name_my,tbl_clm_symbol_my,tbl_clm_ltp_my,tbl_clm_sector_my;
    //Pie-Chart
    @FXML private PieChart pieChart;
    //Combo-box
    @FXML private ComboBox combo_stocks;
    //Calulator page injections
    @FXML private TextField tf_buy_rate,tf_sell_rate,tf_stock_quantity;
    @FXML private Label lb_selling,lb_total_sell,lb_seabon_commission,lb_commisson,lb_db_charge,lb_capital,lb_net_receivable,lb_profit_loss;
    @FXML private  ComboBox combo_capital_gain_tax,combo_sector;
    @FXML private AnchorPane pn_profit_loss;
    @FXML private VBox vbox_colour_change;





    @Override
    public  void initialize(URL location, ResourceBundle resources){
        // intial Implementations
        e= myWebView.getEngine();
        e.load("https://www.nepsealpha.com/trading/chart");
        tbl_clm_symbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        tbl_clm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_clm_ltp.setCellValueFactory(new PropertyValueFactory<>("ltp"));
        tbl_clm_sector.setCellValueFactory(new PropertyValueFactory<>("sector"));
        tbl_clm_symbol_my.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        tbl_clm_name_my.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_clm_ltp_my.setCellValueFactory(new PropertyValueFactory<>("ltp"));
        tbl_clm_sector_my.setCellValueFactory(new PropertyValueFactory<>("sector"));


        //calculator section
       String[] capital_gain_array = new String[]{"7.5%","5%","10%"};

        combo_capital_gain_tax.setItems(FXCollections.observableArrayList(capital_gain_array));

        String[] sector_array = new String[]{"Combined","Mutual Fund","Corporate Debenture"};
        combo_sector.setItems(FXCollections.observableArrayList(sector_array));





//   piechart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Microfinance",3),
                new PieChart.Data("Hydro", 3),
                new PieChart.Data("Commercial Bank", 4),
                new PieChart.Data("Life Insurance",1),
                new PieChart.Data("Finance",1)


        );
        pieChart.setData(pieChartData);

//        pieChart.setTitle("Sectorwise Summary of your STOCKS");
//        pieChart.setTitleSide(Side.BOTTOM);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelsVisible(false);
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(Side.RIGHT);
        pieChartData.stream().forEach(pieData -> {
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Bounds b1 = pieData.getNode().getBoundsInLocal();
                double newX = (b1.getWidth()) / 2 + b1.getMinX();
                double newY = (b1.getHeight()) / 2 + b1.getMinY();
                // Make sure pie wedge location is reset
                pieData.getNode().setTranslateX(0);
                pieData.getNode().setTranslateY(0);
                TranslateTransition tt = new TranslateTransition(
                        Duration.millis(1500), pieData.getNode());
                tt.setByX(newX);
                tt.setByY(newY);
                tt.setAutoReverse(true);
                tt.setCycleCount(2);
                tt.play();
            });
        });









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
            label_location.setText("/home/profit_loss_calculator");
            label_category.setText("PROFIT/LOSS CALCULATOR");
            pn_stock_news.toFront();
        }
        else if(event.getSource()==btn_sectorwise){
            label_location.setText("/home/sectorwise");
            label_category.setText("SECTORWISE SUMMARY");
            pn_sectorwise.toFront();
        }
        else if(event.getSource()==btn_stocks){
            label_location.setText("/home/stocks");
            label_category.setText("STOCKS REGISTERED UNDER SEABON");
            pn_stocks.toFront();
        }

}

     //mouse drag and mouse drag over for buttons
    @FXML private void mouseEnter (MouseEvent event){
         ((Button) event.getSource()).setStyle("-fx-background-color: #0055da;");

    }
    @FXML private void mouseExit(MouseEvent event){
        ((Button) event.getSource()).setStyle("-fx-background-color: #1d0024;");

    }


// Handle click of AddToMyStocks
   @FXML private void handleAddToMyStocks(ActionEvent event){
        String stockToBeAdded = combo_stocks.getValue().toString();
      if  (DBUtils.addStock(stockToBeAdded,label_welcome.getText(),event,label_kheladi_type.getText())) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setContentText("Added stock to the database.");
          alert.show();
      }else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setContentText("Sorry. Some problem occured. ");
          alert.show();
      }


    }


    //calculate profit/loss
    @FXML private void handleCalculate(){
        ProfitLossCalculator profitLossCalculator = new ProfitLossCalculator(7,Double.parseDouble(tf_sell_rate.getText()),Double.parseDouble(tf_buy_rate.getText()), Integer.parseInt(tf_stock_quantity.getText()));
        profitLossCalculator.calculateNetGain();

        lb_capital.setText("Capital Gain Tax:  "+ profitLossCalculator.getCGT());
        lb_commisson.setText("B.Commission:  "+ profitLossCalculator.calculateBrokerCommission());
        lb_db_charge.setText("DP Charge:  "+profitLossCalculator.getDP_Charge());
        lb_seabon_commission.setText("SEBON Commission:  "+profitLossCalculator.getSebon_fee());
        lb_total_sell.setText("Total Sell Amount:  "+profitLossCalculator.getTotalSellAmount());
        lb_selling.setText("Selling Price:  "+ profitLossCalculator.getSell_price());
        lb_net_receivable.setText("Net Receivable Amount:  "+ profitLossCalculator.getNet_receivableAmount());


        if (profitLossCalculator.calculateNetGain()<0){

            pn_profit_loss.setStyle("-fx-background-color: red;");
            lb_profit_loss.setText("LOSS :"+ profitLossCalculator.calculateNetGain());

            vbox_colour_change.setStyle("-fx-border-color: red");

        }
        else if (profitLossCalculator.calculateNetGain()==0){
            pn_profit_loss.setStyle("-fx-background-color: blue;");
            lb_profit_loss.setText("NEUTRAL :"+ profitLossCalculator.calculateNetGain());
            vbox_colour_change.setStyle("-fx-border-color: blue");
        }
        else {
            pn_profit_loss.setStyle("-fx-background-color: green;");
            lb_profit_loss.setText("PROFIT :"+ profitLossCalculator.calculateNetGain());
            vbox_colour_change.setStyle("-fx-border-color: green");
        }
    }

   // Dashboard welcome and type label
    public void setUserInformation(String username, String type, ArrayList<stockModel> stocks, ArrayList<stockModel> myStocks){
        label_welcome.setText(username);
        label_kheladi_type.setText(type);
        for (int i=0; i<stocks.size();i++) {
        stocks_table.getItems().add(stocks.get(i));


        }
        for (int i=0; i<myStocks.size();i++) {
            System.out.println(myStocks.get(i));
            stocks_table_my.getItems().add(myStocks.get(i));

}

        // Find out remaining stock which is not in my stocks for adding it to combobox.
        ArrayList<stockModel> tempStocks = stocks;
        ArrayList<String> remainingStocksSymbol = new ArrayList();
        for (stockModel s: myStocks
             ) {
            tempStocks.removeIf(t->(t.getSymbol().equals( s.getSymbol())));}

        for (stockModel stock:tempStocks
             ) {

            remainingStocksSymbol.add(stock.getSymbol());

        }
        combo_stocks.setItems(FXCollections.observableArrayList(remainingStocksSymbol));
}
}
