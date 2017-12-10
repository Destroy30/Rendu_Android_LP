package fr.iut.tp2_lp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Classe modélisant les paramètres de l'application récupérés/enregistrés sur le système
 */

public class UserStorage {

    /**
     * Nom de l'utilisateur de l'application
     */
    private static String userName=null;

    /**
     * Email de l'utilisateur de l'application
     */
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

    /**
     * Methode permettant de sauvegarder sur le système les informations de l'utilisateur
     * @param context
     */

    public static void saveUserInfo(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("USER_NAME", UserStorage.getUserName());
        editor.putString("USER_EMAIL", UserStorage.getEmail());
        editor.apply();
    }

    /**
     * Méthode permettant de récupérer les informations sur l'utilisateur depuis le système et de setter les atributs avec ses données
     * @param context
     */
    public static void getUserInfo(Context context) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        String name = shared.getString("USER_NAME",null);
        String email = shared.getString("USER_EMAIL",null);
        UserStorage.setUserDatas(name,email);
    }

    /**
     * Méthode permettant de supprimer les informations de l'utilisateur contenues dans le système et dans l'application
     * @param context
     */

    public static void removeUserInfo(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove("USER_NAME");
        editor.remove("USER_EMAIL");
        UserStorage.userName=null;
        UserStorage.email=null;
        editor.apply();
    }



}
