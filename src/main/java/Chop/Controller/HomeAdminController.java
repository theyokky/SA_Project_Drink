package Chop.Controller;

import Chop.Model.Customer;
import Chop.Model.Product;
import Chop.Model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeAdminController {
    @FXML TableView table = new TableView();
    @FXML Label name_label;
    @FXML Label id_label;
    @FXML Label tel_label;
    @FXML Label user_label;
    String[] s;
    private Staff selectStaff;

    private  ObservableList<Staff> data =
            FXCollections.observableArrayList(
                    StaffAPI.getAllCashier()
            );

    public void initialize() {
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(
                new PropertyValueFactory<Staff, String>("name"));

        TableColumn lastname = new TableColumn("Lastname");
        lastname.setCellValueFactory(
                new PropertyValueFactory<Staff, String>("lastname"));

        TableColumn user = new TableColumn("Username");
        user.setCellValueFactory(
                new PropertyValueFactory<Staff, String>("user"));

        table.setItems(data);
        table.getColumns().addAll(user,name,lastname);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                showSelectedItem((Staff) newValue);
            }
        });
    }


    private void showSelectedItem(Staff staff){
        selectStaff = staff;

        name_label.setText(selectStaff.getName() + " " + selectStaff.getLastname());
        id_label.setText(selectStaff.getId().toString());
        tel_label.setText(selectStaff.getTel());
        user_label.setText(selectStaff.getUser());

    }


    @FXML public void deleteAction(ActionEvent event) throws IOException {
        if(selectStaff != null){
            StaffAPI.deletestaff(selectStaff);
            data =
                    FXCollections.observableArrayList(
                            StaffAPI.getAllCashier()
                    );
            table.setItems(data);
            table.refresh();
        }

    }

    @FXML public void addAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AddStaff.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();

    }

    @FXML public void logoutAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Home.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();
    }
}
