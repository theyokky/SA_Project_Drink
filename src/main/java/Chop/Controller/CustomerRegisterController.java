package Chop.Controller;

import Chop.Manager.Checker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerRegisterController {
    @FXML TextField user_textfield;
    @FXML TextField name_textfield;
    @FXML TextField phone_textfield;
    @FXML PasswordField pass_passwordfield;
    @FXML PasswordField pass_con_passwordfield;
    @FXML Label name_label;
    @FXML Label user_label;
    @FXML Label phone_label;
    @FXML Label pass_label;
    @FXML Label pass_con_label;
    Checker checker = new Checker();


    @FXML public void registerAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String user = user_textfield.getText();
        String name = name_textfield.getText();
        String phone = phone_textfield.getText();
        String pass = pass_passwordfield.getText();
        String con_pass = pass_con_passwordfield.getText();

        int check = 5;

        if (checker.checkUserName(user)) {
            user_label.setText("");
            check -= 1;
        }
        else {
            user_label.setText("* Username สั้นเกินไป");
        }

        if (checker.checkAllName(name)){
            name_label.setText("");
            check -= 1;
        }
        else{
            name_label.setText("* ชื่อควรเป็นตัวอักษรและไม่สั้นเกินไป !");
        }

        if (checker.checkPhoneNumber(phone)) {
            phone_label.setText("");
            check -= 1;
        }
        else {
            phone_label.setText("* เบอร์โทรไม่ถูกต้องตามหลัก !");
        }

        if (checker.checkPassword(pass)){
            pass_label.setText("");
            check -= 1;
        }
        else{
            pass_label.setText("* รหัสผ่านสั้นเกินไป !");
        }

        if (pass.equals(con_pass)){
            pass_con_label.setText("");
            check -= 1;
        }
        else{
            pass_con_label.setText("* รหัสผ่านไม่ตรงกัน !");
        }

        System.out.println(check);

        if (check == 0 ){
            Alert alert =
                    new Alert(Alert.AlertType.CONFIRMATION,
                            "ยืนยันลงทะเบียนหรือไม่ ?",
                            ButtonType.OK,
                            ButtonType.CANCEL);
            alert.setTitle("Happy Condo");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                register(
                    name_textfield.getText(), name_textfield.getText(), user_textfield.getText()
                        ,pass_passwordfield.getText(),pass_con_passwordfield.getText()
                        ,phone_textfield.getText()
                );
            }
        }

    }



    @FXML public void backAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        toPage(stage, "/Home.fxml");
    }

    private void toPage(Stage stage, String page) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(page));
        Scene scene = new Scene(loader.load());

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();
    }

    public void register(String name, String lastname, String username, String password, String c_password,
                           String tel
    ){
        JSONObject jsonObject = null;
        try {
            jsonObject =
                    Unirest.post("http://systemanalasisapi.herokuapp.com/api/customer/register")
                    .queryString("name",name)
                    .queryString("lastname",lastname)
                    .queryString("username",username)
                    .queryString("password",password)
                    .queryString("c_password",c_password)
                    .queryString("tel",tel)
                    .asJson().getBody().getObject();

            jsonObject.get("token").toString();
            login(user_textfield.getText(), pass_passwordfield.getText());
            Stage stage = (Stage) name_textfield.getScene().getWindow();
            toPage(stage, "/HomeCustomer.fxml");
        }
        catch (JSONException | IOException e){
            if(!jsonObject.isNull("username"))
            user_label.setText(jsonObject.getJSONArray("username").getString(0));
            if(!jsonObject.isNull("tel"))
            phone_label.setText(jsonObject.getJSONArray("tel").getString(0));
        }
    }

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
