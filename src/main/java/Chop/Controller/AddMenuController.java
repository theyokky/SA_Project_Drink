package Chop.Controller;

import Chop.Manager.Checker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddMenuController {
    @FXML ImageView picture;
    private String picture_path;
    @FXML Label menu_label;
    @FXML Label price_label;
    @FXML Label size_label;
    @FXML Label date1_label;
    @FXML Label date2_label;
    @FXML TextField menu_textfield;
    @FXML TextField size_textfield;
    @FXML TextField price_textfield;
    @FXML DatePicker date1;
    @FXML DatePicker date2;

    Checker checker = new Checker();

    @FXML public void addAction()  {
        String menu = menu_textfield.getText();
        String size = size_textfield.getText();
        String price = price_textfield.getText();
        Object date1_ = date1.getValue();
        String date1_string = String.valueOf(date1_);
        Object date2_ = date2.getValue();
        String date2_string = String.valueOf(date2_);

        int check = 5;

        if (!(menu.equals(""))) {
            menu_label.setText("");
            check -= 1;
        }
        else {
            menu_label.setText("* กรุณากรอกชื่อเมนู !");
        }

        if (!(size.equals(""))){
            size_label.setText("");
            check -= 1;
        }
        else{
            size_label.setText("* กรุณากรอกไซส์ !");
        }

        if (checker.checkPrice(price)) {
            price_label.setText("");
            check -= 1;
        }
        else {
            price_label.setText("* ราคาต้องเป็นตัวเลข !");
        }

        if (!(date1_string.equals("null"))){
            date1_label.setText("");
            check -= 1;
        }
        else{
            date1_label.setText("*");
        }

        if (!(date2_string.equals("null"))){
            date2_label.setText("");
            check -= 1;
        }
        else{
            date2_label.setText("*");
        }

        System.out.println(check);

        if (check == 0 ){
            Alert alert =
                    new Alert(Alert.AlertType.CONFIRMATION,
                            "ยืนยันเพิ่มเมนูหรือไม่ ?",
                            ButtonType.OK,
                            ButtonType.CANCEL);
            alert.setTitle("DrinkTea");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("OK");
            }
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
