package Chop.Controller;

import Chop.Manager.Checker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class StaffEditController {
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

    @FXML public void editAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String user = user_textfield.getText();
        String name = name_textfield.getText();
        String phone = phone_textfield.getText();
        String pass = newpass_passwordfield.getText();
        String con_pass = newpass_con_passwordfield.getText();

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
            newpass_label.setText("");
            check -= 1;
        }
        else{
            newpass_label.setText("* รหัสผ่านสั้นเกินไป !");
        }

        if (pass.equals(con_pass)){
            newpass_con_label.setText("");
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

        }

    }

    @FXML public void backAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HomeStaff.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();
    }
}
