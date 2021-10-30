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
    public boolean checkAboutAllRoom(String room){
        if (!room.equals("null")){
            return true;
        }
        return false;
    }

    public boolean checkID(String id){
        int ascii;
        int flag = 0;
        if (id.length() == 5){
            for (int i=0 ; i<id.length() ; i++){
                ascii = (int) id.charAt(i);
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
    public String checkPriority(String priority){
        if (!priority.equals("null")){
            if (priority.equals("ทั่วไป")){
                priority = "0";
            }
            else if (priority.equals("ส่วนบุคคล")){
                priority = "1";
            }
            else if (priority.equals("ลับมาก")){
                priority = "2";
            }
            return priority;
        }
        return "";
    }
    public boolean checkTrackingNumber(String tracking_number) {
        int ascii;
        int flag = 0;
        if (tracking_number.length() >= 5){
            for (int i=0 ; i<tracking_number.length() ; i++){
                ascii = (int) tracking_number.charAt(i);
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

