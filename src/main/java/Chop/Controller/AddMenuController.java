package Chop.Controller;

import Chop.Manager.Checker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AddMenuController {
    @FXML ImageView picture;
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

    @FXML Button addBtn;
    @FXML ImageView image;
    private String picture_path;

    public void initialize() throws IOException {
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg"));
                File file = chooser.showOpenDialog(addBtn.getScene().getWindow());
                if (file != null) {
                    try {
                        File destDir = new File("images");
                        destDir.mkdirs();
                        String[] fileSplit = file.getName().split("\\.");
                        String filename = file.getName();
                        picture_path =  filename;
                        Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath() + System.getProperty("file.separator") + filename);
                        Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println(target.toUri().toString());
                        image.setImage(new Image(target.toUri().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML public void addAction()  {
        String menu = menu_textfield.getText();
        String size = size_textfield.getText();
        String price = price_textfield.getText();
        Object date1_ = date1.getValue();
        String date1_string = String.valueOf(date1_);
        Object date2_ = date2.getValue();
        String date2_string = String.valueOf(date2_);

        menu_label.setText("");
        size_label.setText("");
        price_label.setText("");
        date1_label.setText("");
        date2_label.setText("");

        int check = 5;

        if (!(menu.equals(""))) {
            check -= 1;
        }
        else {
            menu_label.setText("* กรุณากรอกชื่อเมนู !");
        }

        if (!(size.equals(""))){
            check -= 1;
        }
        else{
            size_label.setText("* กรุณากรอกไซส์ !");
        }

        if (checker.checkPrice(price)) {
            check -= 1;
        }
        else {
            price_label.setText("* ราคาต้องเป็นตัวเลข !");
        }

        if (!(date1_string.equals("null"))){
            check -= 1;
        }
        else{
            date1_label.setText("*");
        }

        if (!(date2_string.equals("null"))){
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
                try {
                    ProductAPI.createProduct(
                            Integer.parseInt(price_textfield.getText()),
                            menu_textfield.getText(),
                            size_textfield.getText(),
                            date1.getValue().format(
                                    DateTimeFormatter.ofPattern("yyyy/MM/dd")
                            ),
                            date2.getValue().format(
                                    DateTimeFormatter.ofPattern("yyyy/MM/dd")
                            ),
                            picture_path
                    );
                }catch (JSONException e){
                    JSONObject er =  Error.getInstance().getErrorJSON();
                    if(!er.isNull("P_name"))
                        menu_label.setText(er.getString("P_name"));
                    if(!er.isNull("P_price"))
                        price_label.setText(er.getString("P_price"));
                    if(!er.isNull("P_size"))
                        size_label.setText(er.getString("P_size"));
                    if(!er.isNull("P_makeDate"))
                        date1_label.setText(er.getString("P_makeDate"));
                    if(!er.isNull("P_expirationDate"))
                        date2_label.setText(er.getString("P_expirationDate"));
                    if(!er.isNull("P_img"))
                        menu_label.setText(er.getString("P_img"));
                }
            }
        }

        System.out.println(picture_path);
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
