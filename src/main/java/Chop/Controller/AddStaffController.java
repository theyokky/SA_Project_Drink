package Chop.Controller;

import Chop.Manager.Checker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Optional;

public class AddStaffController {
    @FXML TextField user_textfield;
    @FXML TextField name_textfield;
    @FXML TextField lastname_textfield;
    @FXML TextField tel_textfield;
    @FXML PasswordField pass_passwordfield;
    @FXML PasswordField pass_con_passwordfield;
    @FXML Label name_label;
    @FXML Label lastname_label;
    @FXML Label user_label;
    @FXML Label tel_label;
    @FXML Label pass_label;
    @FXML Label passcon_label;
    Checker checker = new Checker();


    @FXML public void addAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String user = user_textfield.getText();
        String name = name_textfield.getText();
        String lastname = name_textfield.getText();
        String phone = tel_textfield.getText();
        String pass = pass_passwordfield.getText();
        String con_pass = pass_con_passwordfield.getText();

        int check = 6;

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

        if (checker.checkAllName(lastname)){
            lastname_label.setText("");
            check -= 1;
        }
        else{
            lastname_label.setText("* นามสกุลควรเป็นตัวอักษรและไม่สั้นเกินไป !");
        }

        if (checker.checkPhoneNumber(phone)) {
            tel_label.setText("");
            check -= 1;
        }
        else {
            tel_label.setText("* เบอร์โทรไม่ถูกต้องตามหลัก !");
        }

        if (checker.checkPassword(pass)){
            pass_label.setText("");
            check -= 1;
        }
        else{
            pass_label.setText("* รหัสผ่านสั้นเกินไป !");
        }

        if (pass.equals(con_pass)){
            passcon_label.setText("");
            check -= 1;
        }
        else{
            passcon_label.setText("* รหัสผ่านไม่ตรงกัน !");
        }

        System.out.println(check);

        if (check == 0 ){
            Alert alert =
                    new Alert(Alert.AlertType.CONFIRMATION,
                            "ยืนยันเพิ่มพนักงานหรือไม่ ?",
                            ButtonType.OK,
                            ButtonType.CANCEL);
            alert.setTitle("DrinkTea");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    StaffAPI.createCashier(
                            name_textfield.getText(),
                            lastname_textfield.getText(),
                            tel_textfield.getText(),
                            user_textfield.getText(),
                            pass_passwordfield.getText(),
                            pass_con_passwordfield.getText()
                    );
                }catch (JSONException e){
                    JSONObject er = Error.getInstance().getErrorJSON();
                    if(!er.isNull("name"))
                        name_label.setText(er.getString("name"));
                    if(!er.isNull("lastname"))
                        lastname_label.setText(er.getString("lastname"));
                    if(!er.isNull("tel"))
                        tel_label.setText(er.getString("tel"));
                    if(!er.isNull("username"))
                        user_label.setText(er.getString("username"));
                    if(!er.isNull("password"))
                        pass_label.setText(er.getString("password"));
                    if(!er.isNull("c_password"))
                        passcon_label.setText(er.getString("c_password"));
                }
            }
        }

    }

    @FXML public void backAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HomeAdmin.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();
    }
}
