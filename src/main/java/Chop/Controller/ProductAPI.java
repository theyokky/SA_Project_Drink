package Chop.Controller;

import Chop.Model.Product;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;

public class ProductAPI {
    public static ArrayList<Product> getAllProduct(){
        ArrayList<Product> products = new ArrayList<>();
        JSONArray productsJSON = Unirest.get("http://systemanalasisapi.herokuapp.com/api/products")
                .asJson().getBody().getObject().getJSONArray("products");
        for (int i=0;i<productsJSON.length();i++){
            JSONObject productJSON = productsJSON.getJSONObject(i);
            products.add(new Product(
                    productJSON.getInt("id"),
                    productJSON.getInt("P_price"),
                    productJSON.getString("P_name"),
                    productJSON.getString("P_size"),
                    productJSON.getString("P_makeDate"),
                    productJSON.getString("P_expirationDate")
            ));
        }
        return products;
    }

    public static void createProduct(Integer price,
                                     String name,
                                     String size,
                                     String make_date,
                                     String expiration_date,
                                     String p_img
                                    ){
        System.out.println(Unirest.post(
                "http://systemanalasisapi.herokuapp.com/api/product/create")
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .queryString("P_name",name)
                .queryString("P_price", price)
                .queryString("P_size",size)
                .queryString("P_makeDate",make_date)
                .queryString("P_expirationDate",expiration_date)
                .queryString("P_img", p_img)
                .asJson().getBody()
        );
    }

    public static void editProduct(Product product){
        System.out.println(Unirest.post(
                "http://systemanalasisapi.herokuapp.com/api/product/edit/"+product.getId())
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .queryString("P_name",product.getName())
                .queryString("P_price", product.getPrice())
                .queryString("P_size",product.getSize())
                .queryString("P_makeDate",product.getMake_date())
                .queryString("P_expirationDate",product.getExpiration_date())
                .queryString("P_img", "12345")
                .asJson().getBody()
        );
    }

    public static void deleteProduct(Product product){
        System.out.println(Unirest.post(
                "http://systemanalasisapi.herokuapp.com/api/product/delete/"+product.getId())
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .asJson().getBody()
        );
    }
}
