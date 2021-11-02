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
    
    private Order_list selected_product = null;

    private ObservableList<Order_list> data =
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
                showSelectedItem((Order_list) newValue);
            }
        });

        refreshPrice();
    }

    private void showSelectedItem(Order_list product){
        selected_product = product;
        menu_label.setText(product.getName());


//        imageView.setImage(new Image(product.getImagePath()));
    }

    @FXML private void deleteAction(){
        if(selected_product != null){
            System.out.println(Unirest.post("http://systemanalasisapi.herokuapp.com/api/order/list/delete/"
                    + selected_product.getRelateOrderList())
                    .header("Authorization","bearer " + Auth.getInstance().getToken())
                    .asString().getBody());
            data = FXCollections.observableArrayList(
                            getAllProduct()
                    );
            table.setItems(data);
            table.refresh();
            refreshPrice();
        }
    }

    @FXML private void payAction(){
        if(data.size() > 0){
            System.out.println(Unirest.post(
                    "http://systemanalasisapi.herokuapp.com/api/order/confirm/"
                    + Auth.getInstance().getC_order_in())
                    .header("Authorization","bearer " + Auth.getInstance().getToken())
                    .asString().getBody());
            Auth.getInstance().reset_c_order_in();
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
        price_label.setText("ราคารวม : " + Order_list.getTotalCostOfProducts(getAllProduct()));
    }
    //==============================================================================================
    //Api get all product
    private ArrayList<Order_list> getAllProduct(){
        ArrayList<Order_list> products = new ArrayList<>();
        System.out.println(Unirest.post("http://systemanalasisapi.herokuapp.com/api/order/basket")
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .queryString("o_id",Auth.getInstance().getC_order_in())
                .queryString("cus_id",Auth.getInstance().getUser().getString("userable_id"))
                .asString().getBody());
        JsonNode productsJSON =
                Unirest.post("http://systemanalasisapi.herokuapp.com/api/order/basket")
                        .header("Authorization","bearer " + Auth.getInstance().getToken())
                        .queryString("o_id",Auth.getInstance().getC_order_in())
                        .queryString("cus_id",Auth.getInstance().getUser().getString("userable_id"))
                        .asJson().getBody();
        System.out.println(Auth.getInstance().getC_order_in());
        System.out.println(Auth.getInstance().getUser().getString("userable_id"));
        System.out.println(productsJSON);
        for (int i=0;i<productsJSON.getArray().length();i++){
            JSONObject productJSON = productsJSON.getArray().getJSONObject(i);
            Order_list product = new Order_list(
                    productJSON.getInt("product_id"),
                    productJSON.getInt("P_price"),
                    productJSON.getString("P_name"),
                    productJSON.getString("P_size"),
                    productJSON.getString("P_makeDate"),
                    productJSON.getString("P_expirationDate")
            );
            product.setRelateOrderList(productJSON.getString("id"));
            product.setQuantity(productJSON.getInt("quantity"));
            products.add(product);
        }
        return products;
    }
}
