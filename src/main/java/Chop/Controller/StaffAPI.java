package Chop.Controller;

import Chop.Model.Product;
import Chop.Model.Staff;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;

public class StaffAPI {
    public static ArrayList<Staff> getAllCashier(){
        ArrayList<Staff> staffs = new ArrayList<>();
        JSONArray staffsJSON = Unirest
                .get("http://systemanalasisapi.herokuapp.com/api/staff/cashiers")
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .asJson().getBody().getObject().getJSONArray("staffs");
        for (int i=0;i<staffsJSON.length();i++){
            JSONObject staffsJSONJSONObject = staffsJSON.getJSONObject(i);
            staffs.add(new Staff(
                    staffsJSONJSONObject.getInt("id"),
                    staffsJSONJSONObject.getString("name"),
                    staffsJSONJSONObject.getString("lastname"),
                    staffsJSONJSONObject.getString("tel"),
                    staffsJSONJSONObject.getString("username"),
                    staffsJSONJSONObject.getString("password"),
                    staffsJSONJSONObject.getString("Staff_role")
            ));
        }
        return staffs;
    }

    public static void createCashier(
                                     String name,
                                     String lastname,
                                     String tel,
                                     String user,
                                     String pass,
                                     String c_pass
    ) throws JSONException {
        JSONObject obj = Unirest.post(
                "http://systemanalasisapi.herokuapp.com/api/staff/register")
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .queryString("name",name)
                .queryString("lastname",lastname)
                .queryString("username",user)
                .queryString("password",pass)
                .queryString("c_password",c_pass)
                .queryString("tel",tel)
                .asJson().getBody().getObject();
        Error.getInstance().setErrorJSON(obj);
        System.out.println(obj.getString("token"));
    }

    public static void deletestaff(Staff staff){
        System.out.println(Unirest.post(
                "http://systemanalasisapi.herokuapp.com/api/staff/delete/"+staff.getId())
                .header("Authorization","bearer " + Auth.getInstance().getToken())
                .asJson().getBody()
        );
    }
}
