package Chop.Model;

import java.util.ArrayList;

public class Order_list {
    private Integer id;
    private Integer price;
    private String name;
    private String size;
    private String make_date;
    private String expiration_date;
    private String relateOrderList;
    private Integer quantity;

    public Order_list() {}
    public Order_list(Integer id,
                   Integer price,
                   String name,
                   String size,
                   String make_date,
                   String expiration_date) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.size = size;
        this.make_date = make_date;
        this.expiration_date = expiration_date;
    }

    public Integer getId() {
        return id;
    }
    public Integer getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public String getSize() {
        return size;
    }
    public String getMake_date() {
        return make_date;
    }
    public String getExpiration_date() {
        return expiration_date;
    }
    public String getRelateOrderList() {
        return relateOrderList;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public static int getTotalCostOfProducts(ArrayList<Order_list> products){
        int total = 0;
        for (Order_list product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
    public void setMake_date(String make_date) {
        this.make_date = make_date;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setRelateOrderList(String relateOrderList) {
        this.relateOrderList = relateOrderList;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
