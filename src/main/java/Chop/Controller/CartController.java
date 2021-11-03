package Chop.Controller;

import Chop.Model.Customer;
import Chop.Model.Order_list;
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
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CartController {
    @FXML TableView table = new TableView();
    @FXML Label menu_label;
    @FXML Label price_label;
    @FXML Label size_label;
    
    private Order_list selected_product = null;

    private ObservableList<Order_list> data =
            FXCollections.observableArrayList(
                    Order.getInstance().getItems()
            );

    public void initialize() {
        TableColumn menu = new TableColumn("Menu");
        menu.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("name"));

        table.setItems(data);
        table.getColumns().addAll(menu);

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                showSelectedItem((Order_list) newValue);
            }
        });

        refreshPrice();
    }

    private void showSelectedItem(Order_list product){
        selected_product = product;
        menu_label.setText(product.getName());
        size_label.setText("Size : " + product.getSize());
    }

    @FXML private void deleteAction(){
        if(selected_product != null){
            Order.getInstance().removeProduct(selected_product);
            data = FXCollections.observableArrayList(
                    Order.getInstance().getItems()
                    );
            table.setItems(data);
            table.refresh();
            refreshPrice();
        }
    }

    @FXML private void payAction(){
        if(data.size() > 0){
            Order.getInstance().pay();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/HomeCustomer.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) table.getScene().getWindow();

            stage.setTitle("DrinkTea");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML private void backAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HomeCustomer.fxml"));
        Scene scene = new Scene(loader.load());

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        stage.setTitle("DrinkTea");
        stage.setScene(scene);
        stage.show();
    }

    private void refreshPrice(){
        price_label.setText("ราคารวม : "
                + Order_list.getTotalCostOfProducts(Order.getInstance().getItems()));
    }
}
