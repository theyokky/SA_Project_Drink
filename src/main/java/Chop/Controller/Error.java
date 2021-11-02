package Chop.Controller;

import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

public class Error {
    private static Error INSTANCE = null;
    private JSONObject errorJSON = null;

    public static Error getInstance(){
        if(INSTANCE == null)
            INSTANCE = new Error();
        return INSTANCE;
    }

    public JSONObject getErrorJSON() {
        return errorJSON;
    }

    public void setErrorJSON(JSONObject errorJSON) {
        this.errorJSON = errorJSON;
    }
}
