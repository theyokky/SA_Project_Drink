package Chop.Controller;

import Chop.Manager.Checker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerEditController {
    @FXML TextField user_textfield;
    @FXML TextField name_textfield;
    @FXML TextField phone_textfield;
    @FXML PasswordField oldpass_passwordfield;
    @FXML PasswordField newpass_passwordfield;
    @FXML PasswordField newpass_con_passwordfield;
    @FXML Label name_label;
    @FXML Label user_label;
    @FXML Label phone_label;
    @FXML Label oldpass_label;
    @FXML Label newpass_label;
    @FXML Label newpass_con_label;
    Checker checker = new Checker();

    public void initialize()
    {
        Auth auth = Auth.getInstance();
        JSONObject obj = auth.getUser();
        user_textfield.setText(obj.getString("username"));
        name_textfield.setText(obj.getString("name"));
        phone_textfield.setText(obj.getString("tel"));
    }

    @FXML public void editAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String user = user_textfield.getText();
        String name = name_textfield.getText();
        String phone = phone_textfield.getText();
        String pass = newpass_passwordfield.getText();
        String con_pass = newpass_con_passwordfield.getText();

        user_label.setText("");
        name_label.setText("");
        phone_label.setText("");
        newpass_label.setText("");
        newpass_con_label.setText("");

        int check = 5;

        if (checker.checkUserName(user)) {
            check -= 1;
        }
        else {
            user_label.setText("* Username สั้นเกินไป");
        }

        if (checker.checkAllName(name)){
            check -= 1;
        }
        else{
            name_label.setText("* ชื่อควรเป็นตัวอักษรและไม่สั้นเกินไป !");
        }

        if (checker.checkPhoneNumber(phone)) {
            check -= 1;
        }
        else {
            phone_label.setText("* เบอร์โทรไม่ถูกต้องตามหลัก !");
        }

        if (checker.checkPassword(pass)){
            check -= 1;
        }
        else{
            newpass_label.setText("* รหัสผ่านสั้นเกินไป !");
        }

        if (pass.equals(con_pass)){
            check -= 1;
        }
        else{
            newpass_con_label.setText("* รหัสผ่านไม่ตรงกัน !");
        }

        System.out.println(check);

        if (check == 0 ){
            Alert alert =
                    new Alert(Alert.AlertType.CONFIRMATION,
                            "ยืนยันแก้ไขข้อมูลหรือไม่ ?",
                            ButtonType.OK,
                            ButtonType.CANCEL);
            alert.setTitle("Happy Condo");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                editUser();
            }
        }

    }



    @FXML public void backAction(ActionEvent event) throws IOException {

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        toPage(stage, "/HomeCustomer.fxml");
    }

    private void editUser(){
        JSONObject obj = null;
        try {
            obj =  Unirest.post(
                    "http://systemanalasisapi.herokuapp.com/api/user/edit/"
                            +Auth.getInstance().getUser().getString("id"))
                    .header("Authorization","bearer " + Auth.getInstance().getToken())
                    .queryString("username",user_textfield.getText())
                    .queryString("password",oldpass_passwordfield.getText())
                    .queryString("new_password",newpass_passwordfield.getText())
                    .queryString("c_password",newpass_con_passwordfield.getText())
                    .queryString("name",name_textfield.getText())
                    .queryString("lastname",name_textfield.getText())
                    .queryString("tel",phone_textfield.getText())
                    .asJson().getBody().getObject();
            System.out.println(obj.get("token"));
            toPage((Stage) user_textfield.getScene().getWindow()
                    , "/HomeCustomer.fxml");
        }catch (JSONException e){
            if(!obj.isNull("username"))
                user_label.setText(obj.getString("username"));
            if(!obj.isNull("password"))
                oldpass_label.setText(obj.getString("password"));
            if(!obj.isNull("new_password"))
                newpass_label.setText(obj.getString("new_password"));
            if(!obj.isNull("c_password"))
                newpass_con_label.setText(obj.getString("c_password"));
            if(!obj.isNull("name"))
                name_label.setText(obj.getString("name"));
            if(!obj.isNull("lastname"))
                name_label.setText(obj.getString("lastname"));
            if(!obj.isNull("tel"))
                phone_label.setText(obj.getString("tel"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toPage(Stage stage, String page) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(page));
        Scene scene = new Scene(loader.load());

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();
    }
}
