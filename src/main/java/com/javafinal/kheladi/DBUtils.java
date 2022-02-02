package com.javafinal.kheladi;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DBUtils {


    // static method to change scenes.
    public static void changeScene(ActionEvent event , String fxmlFile, String title, String username, String type, ArrayList stocks, ArrayList myStocks){
        Parent root = null;

        if(username !=null && type !=null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root= loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, type ,stocks,myStocks);

            } catch (IOException e){
                e.printStackTrace();
            }} else {
                try {
                    root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

//Double cast
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 700, 500));



    }
    // user Signup
    public static void signUpUser(ActionEvent event, String username, String password, String type){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists= null ;
        ResultSet resultSet = null ;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/kheladi","root", "");
            System.out.println("Connected to database Successfully");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM authentication WHERE username=?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User Already Exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            }else {
                psInsert= connection.prepareStatement("INSERT INTO authentication ( username, password ,type) VALUES (?,?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,type);
                psInsert.executeUpdate();
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setContentText("Account Created Successfully!");
alert.show();
//                changeScene(event, "logged-in.fxml","Welcome",username,type);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        // Now we should close the connection so that there is no chance of memory leakage.
        finally {
            if (resultSet !=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }


            if (psCheckUserExists !=null){
                try{
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert !=null){
                try{
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection !=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }

    }
    // user login
    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/kheladi","root", "");
            System.out.println("connected to database successfully");
            preparedStatement = connection.prepareStatement("SELECT password, type FROM authentication WHERE username=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("User not found in database. ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are Incorrect!");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedChannel = resultSet.getString("type");
                    if(retrievedPassword.equals(password)){


                        changeScene(event, "logged-in.fxml","Welcome!", username, retrievedChannel,getStocks(),getMyStocks(username));
                    }else {
                        System.out.println("Passwords donot match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect");
                        alert.show();

                    }
                }
            }


        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet !=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }


            if (preparedStatement !=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (connection !=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
    }
    // returns a list of stockModel (all stocks available)
    public static ArrayList<stockModel> getStocks(){
//        ArrayList stocks = new ArrayList<String>();
        ArrayList stocks = new ArrayList<stockModel>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/kheladi","root", "");
            System.out.println("Get Stocks entered");
            preparedStatement = connection.prepareStatement("SELECT * FROM stocks");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("No stocks data found ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No Stock data found");
                alert.show();
            }else {

                while (resultSet.next()){
                    String symbol = resultSet.getString("Symbol");
                    String name = resultSet.getString("Name");
                    int ltp = resultSet.getInt("LTP");
                    String sector = resultSet.getString("Type");

                    stocks.add(new stockModel(symbol,name,ltp,sector));


                }
                return stocks;
            }


        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet !=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }


            if (preparedStatement !=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (connection !=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
        return stocks;
    }


    // returns a list of stockModel( only those stocks assigned to a username)
    public static ArrayList<stockModel> getMyStocks(String username){
//        ArrayList stocks = new ArrayList<String>();
        ArrayList myStocks = new ArrayList<stockModel>();
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
             connection = DriverManager.getConnection("jdbc:mysql://localhost/kheladi","root", "");
            System.out.println("Get my stocks entered");


            preparedStatement = connection.prepareStatement("SELECT * from stocks WHERE usernames LIKE ?");
            preparedStatement.setString(1, "%" + username+ "%");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("No stocks data found ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No Stock data found");
                alert.show();
            }else {

                while (resultSet.next()){
                    String symbol = resultSet.getString("Symbol");
                    String name = resultSet.getString("Name");
                    int ltp = resultSet.getInt("LTP");
                    String sector = resultSet.getString("Type");

                    myStocks.add(new stockModel(symbol,name,ltp,sector));
     }
 return myStocks;
            }


        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet !=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }


            if (preparedStatement !=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (connection !=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }
        return myStocks;
    }

    public static boolean addStock( String symbol, String username,ActionEvent event, String type){
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/kheladi","root", "");
            System.out.println("Add stocks entered");


            preparedStatement = connection.prepareStatement("UPDATE stocks SET usernames = concat(usernames,?) WHERE Symbol = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2,symbol);
            preparedStatement.executeUpdate();

            changeScene(event,"logged-in.fxml","Welcome",username,type,getStocks(),getMyStocks(username));
            System.out.println("Updated successfully");
            return true;
 } catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet !=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }


            if (preparedStatement !=null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (connection !=null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }


        return false;
    }



}
