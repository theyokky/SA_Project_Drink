package Chop.Controller;

import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

public final class Auth {
    private String token;
    private JSONObject user;
    private String role;
    private String c_order_in = null;
    private static  Auth INSTANCE = null;

    public static Auth getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Auth();
        return INSTANCE;
    }

    public void setUser(JSONObject user) {
        this.user = user;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public String getRole() {
        return role;
    }
    public JSONObject getUser() {
        return user;
    }
    public String getC_order_in() {
        if(c_order_in == null){
            c_order_in = Unirest.post("http://systemanalasisapi.herokuapp.com/api/order/create")
                    .header("Authorization","bearer " + token)
                    .asJson().getBody().getObject().getString("order_in_id");
        }
        return c_order_in;
    }

    public boolean isCustomer(){
        try{
            return role.equals("");
        }catch (JSONException e){
            System.out.println(e);
            return false;
        }
    }

    public boolean isCashier(){
        try{
            return role.equals("Cashier");
        }catch (JSONException e){
            return false;
        }
    }
    public boolean isAdmin(){
        try{
            return role.equals("Admin");
        }catch (JSONException e){
            System.out.println(e);
            return false;
        }
    }

    public void reset_c_order_in(){
        this.c_order_in = null;
    }
}
