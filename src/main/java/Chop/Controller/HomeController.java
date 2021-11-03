package Chop.Controller;

import Chop.AccessDatabase.CustomerDataAccessor;
import Chop.Model.Customer;
import Chop.Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.omg.CORBA.Object;
import unirest.shaded.com.google.gson.JsonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class HomeController extends Application {
    @FXML TextField user_textfield;
    @FXML PasswordField pass_passwordfield;
    @FXML Label user_label;

    private CustomerDataAccessor dataAccessor ;

    @Override public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Home.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("DrinkTea");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML public void loginAction(ActionEvent event) throws Exception{

        System.out.println("OK");

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        user_label.setText("");

        String token = login(user_textfield.getText(),pass_passwordfield.getText());
        if(token != null){
            FXMLLoader loader = new FXMLLoader();
            Auth auth = Auth.getInstance();
            if(auth.isAdmin())
                loader.setLocation(getClass().getResource("/HomeAdmin.fxml"));
            else if (auth.isCashier())
                loader.setLocation(getClass().getResource("/HomeStaff.fxml"));
            else loader.setLocation(getClass().getResource("/HomeCustomer.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setTitle("DrinkTea");
            stage.setScene(scene);
            stage.show();
        }
        else {
            pass_passwordfield.setText("");
        }
    }


    @Override
    public void stop() throws Exception {
        if (dataAccessor != null) {
            dataAccessor.shutdown();
        }
    }

    @FXML public void registerAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CustomerRegister.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    //==========================================================================================
    //Authenticate
    public String login(String username, String password){
        JSONObject obj = null;
        try {
            obj =  Unirest.post("http://systemanalasisapi.herokuapp.com/api/login")
                    .queryString("username",username)
                    .queryString("password",password)
                    .asJson().getBody().getObject();
            String token = obj.getString("token");
            Auth auth = Auth.getInstance();
            auth.setToken(token);
            auth.setUser(
                    !obj.isNull("user") ?
                    obj.getJSONObject("user"): null);
            auth.setRole(
                    !obj.isNull("Staff_role") ?
                    obj.getString("Staff_role"): "");
            return token;
        }catch (JSONException e){
            user_label.setText(obj.getString("error"));
            return null;
        }
    }
}
