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

public class HomeStaffController {
    @FXML TableView table = new TableView();
    @FXML Label menu_label;
    @FXML TextField price_textfield;
    private Product selectedProduct = null;

    private ObservableList<Product> data =
            FXCollections.observableArrayList(
                    getAllProduct()
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
        selectedProduct = product;
        price_textfield.setText(product.getPrice()+"");
        menu_label.setText(product.getName());

//        imageView.setImage(new Image(product.getImagePath()));
    }

    @FXML public void addAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/AddMenu.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();

    }

    @FXML public void editAction()  {
        if(selectedProduct != null){
            Product product = new Product(
                    selectedProduct.getId(),
                    Integer.parseInt(price_textfield.getText()),
                    selectedProduct.getName(),
                    selectedProduct.getSize(),
                    selectedProduct.getMake_date(),
                    selectedProduct.getExpiration_date()
            );
            ProductAPI.editProduct(product);
            data = FXCollections.observableArrayList(
                            getAllProduct()
                    );
            table.setItems(data);
            table.refresh();
        }

    }

    @FXML public void deleteAction()  {
        if(selectedProduct != null){
            ProductAPI.deleteProduct(selectedProduct);
            data = FXCollections.observableArrayList(
                    getAllProduct()
            );
            table.setItems(data);
            table.refresh();
        }

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

    //==============================================================================================
    //Api get all product
    private ArrayList<Product> getAllProduct(){
        return ProductAPI.getAllProduct();
    }
}
