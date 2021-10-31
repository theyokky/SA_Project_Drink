package Chop.Manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {
    public boolean checkAllName(String name){
        int ascii;
        int flag = 0;
        if (name.length() >= 3){
            for (int i=0 ; i<name.length() ; i++){
                ascii = (int) name.charAt(i);
                if (!((ascii >= 97 && ascii <=122) || (ascii >= 65 && ascii <= 90))){
                    flag = 1;
                    break;
                }
            }
        }
        else{
            flag = 1;
        }

        if (flag == 1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean checkPhoneNumber(String phone_number){
        int ascii;
        int flag = 0;
        if (phone_number.length() == 10){
            for (int i=0 ; i<phone_number.length() ; i++){
                ascii = (int) phone_number.charAt(i);
                if (!(ascii >= 48 && ascii <=57 )){
                    flag = 1;
                    break;
                }
            }
        }
        else{
            flag = 1;
        }

        if (flag == 1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean checkPassword(String password){
        if (password.length() < 6){
            return false;
        }
        return true;
    }
    public boolean checkUserName(String user_name){
        if ((user_name.length() < 5) && !(user_name.equals("null"))){
            return false;
        }
        return true;
    }
    public boolean checkEmail(String email){
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if (matcher.find()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkPrice(String price){
        int ascii;
        int flag = 0;
        if (price.length() > 0){
            for (int i=0 ; i<price.length() ; i++){
                ascii = (int) price.charAt(i);
                if (!(ascii >= 48 && ascii <=57 )){
                    flag = 1;
                    break;
                }
            }
        }
        else{
            flag = 1;
        }

        if (flag == 1){
            return false;
        }
        else {
            return true;
        }
    }

}

