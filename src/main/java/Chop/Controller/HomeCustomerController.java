package Chop.Controller;

import Chop.Model.Customer;
import Chop.Model.Product;
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
import java.util.ArrayList;

public class HomeCustomerController {
    @FXML TableView table = new TableView();
    @FXML Label menu_label;
    @FXML Label price_label;
    @FXML Spinner<Integer> int_spinner;
    @FXML ComboBox size_combobox;
    String[] s;
    private Product selectedProduct;

    private final ObservableList<Product> data =
            FXCollections.observableArrayList(
                    new Product(00001, 50, "Original bubble tea", "S,M,L", "1/1/1", "2/2/2"),
                    new Product(00002, 50, "Cocoa", "S,M,L,XL", "1/1/1", "2/2/2")
            );

    public void initialize() {
        TableColumn menu = new TableColumn("Menu");
        menu.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("name"));

        table.setItems(data);
        table.getColumns().addAll(menu);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                showSelectedItem((Product) newValue);
            }
        });
    }


    private void showSelectedItem(Product product){
        size_combobox.getItems().clear();
        selectedProduct = product;
        s = product.getSize().split(",");
        // spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 40, 1);
        int_spinner.setValueFactory(valueFactory);

        menu_label.setText(product.getName());
        price_label.setText("ราคารวม : " + 0);

        size_combobox.getItems().addAll(s);

//        imageView.setImage(new Image(product.getImagePath()));
    }

    @FXML public void addAction(ActionEvent event) throws IOException {
        Object size = size_combobox.getValue();
        String size_string = String.valueOf(size);

        System.out.println(int_spinner.getValue());
        System.out.println(size_string);

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
