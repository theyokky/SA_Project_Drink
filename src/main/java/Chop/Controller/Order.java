package Chop.Controller;

import Chop.Model.Order_list;
import Chop.Model.Product;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;

public class Order {
    private static Order INSTANCE = null;
    private ArrayList<Order_list> items = new ArrayList<>();

    public static Order getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Order();
        return INSTANCE;
    }

    public void addProduct(Product product, int quantity){
        items.add(new Order_list(product, quantity));
    }
    public void removeProduct(Order_list product){
        items.remove(product);
    }

    public ArrayList<Order_list> getItems() {
        return items;
    }

    public void resetOrder(){
        items = new ArrayList<>();
    }

    public void pay(){
        Auth auth = Auth.getInstance();

        for (Order_list item : items) {
            System.out.println(Unirest.post("http://systemanalasisapi.herokuapp.com/api/order/add")
                    .header("Authorization", "bearer " + auth.getToken())
                    .queryString("O_id", auth.getC_order_in())
                    .queryString("P_id", item.getId())
                    .queryString("quantity", item.getQuantity())
                    .asJson().getBody().getArray());
        }

        System.out.println(Unirest.post(
                "http://systemanalasisapi.herokuapp.com/api/order/confirm/"
                        + Auth.getInstance().getC_order_in())
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .asString().getBody());
        auth.reset_c_order_in();
        resetOrder();
    }
}
