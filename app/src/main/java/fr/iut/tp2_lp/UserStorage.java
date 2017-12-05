package fr.iut.tp2_lp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by destr_000 on 04/12/2017.
 */

public class UserStorage {

    private static String userName=null;
    private static String email=null;

    public static void setUserDatas(String userName,String email) {
        UserStorage.userName=userName;
        UserStorage.email=email;
    }

    public static void setUserName(String userName) {
        UserStorage.userName=userName;
    }

    public static void setEmail(String email) {
        UserStorage.email=email;
    }

    public static String getUserName() {
        return UserStorage.userName;
    }

    public static String getEmail() {
        return UserStorage.email;
    }

    public static void saveUserInfo(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("USER_NAME", UserStorage.getUserName());
        editor.putString("USER_EMAIL", UserStorage.getEmail());
        editor.apply();
    }

    public static void getUserInfo(Context context) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        String name = shared.getString("USER_NAME",null);
        String email = shared.getString("USER_EMAIL",null);
        UserStorage.setUserDatas(name,email);
    }



}
