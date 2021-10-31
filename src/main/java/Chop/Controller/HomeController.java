package Chop.Controller;

import Chop.AccessDatabase.CustomerDataAccessor;
import Chop.Model.Customer;
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

import java.io.IOException;
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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HomeCustomer.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        HomeCustomerController controller = loader.getController();

        if(!login("jack","jack").equals("")){
            stage.setTitle("DrinkTea");
            stage.setScene(scene);
            stage.show();
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
        try {
            return Unirest.post("http://systemanalasisapi.herokuapp.com/api/customer/login")
                    .queryString("username",username)
                    .queryString("password",password)
                    .asJson().getBody().getObject().get("token").toString();
        }catch (JSONException e){
            return "";
        }
    }

    public String register(String Cus_point, String Cus_right, String Cus_status,
                           String name, String lastname, String username, String password,
                           String tel
    ){
        try {
            return Unirest.post("http://systemanalasisapi.herokuapp.com/api/customer/register")
                    .queryString("Cus_point",Cus_point)
                    .queryString("Cus_right",Cus_right)
                    .queryString("Cus_status",Cus_status)
                    .queryString("name",name)
                    .queryString("lastname",lastname)
                    .queryString("username",username)
                    .queryString("password",password)
                    .queryString("tel",tel)
                    .asJson().getBody().getObject().get("token").toString();
        }
        catch (JSONException e){
            return "";
        }
    }
}
