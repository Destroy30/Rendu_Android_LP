package fr.iut.tp2_lp;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by destr_000 on 27/11/2017.
 */

public class Message {

    public String userName;
    public String content;
    public String userEmail;
    public Long timestamp;
    public DatabaseReference refDatabase;

    public static String GRAVTAR_PREFIX = "https://www.gravatar.com/avatar/";

    public Message() {

    }

    public Message(String content,String userName,String userEmail,Long timestamp) {
        this.userName=userName;
        this.content=content;
        this.userEmail=userEmail;
        this.timestamp=timestamp;
    }

    public boolean sendFromCurrentUser() {
        return (userEmail!=null && userEmail.equals(UserStorage.getEmail()));
    }

    public String getTimeInformations() {
        Date sended = new Date(timestamp);
        Date now = new Date();
        long diffTime = now.getTime()-sended.getTime();
        int secondes = (int) TimeUnit.MILLISECONDS.toSeconds(diffTime);
        String pluriel;
        if(secondes<1) {
            return "(A l'instant)";
        }
        else if(secondes<60) {
            pluriel = secondes > 1 ? "s" : "";
            return "(Il y a "+secondes+" seconde"+pluriel+")";
        }
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(diffTime);
        if(minutes<60) {
            pluriel = minutes > 1 ? "s" : "";
            return "(Il y a "+minutes+" minute"+pluriel+")";
        }
        int heures = (int) TimeUnit.MILLISECONDS.toHours(diffTime);
        if(heures<24) {
            pluriel = heures > 1 ? "s" : "";
            return "(Il y a "+heures+" heure"+pluriel+")";
        }
        int jours = (int) TimeUnit.MILLISECONDS.toDays(diffTime);
        if(jours<365) {
            pluriel = jours > 1 ? "s" : "";
            return "(Il y a "+jours+" jour"+pluriel+")";
        }
        return "(Il y a plus d'un an)";
    }
}
