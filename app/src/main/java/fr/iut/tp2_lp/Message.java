package fr.iut.tp2_lp;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by destr_000 on 27/11/2017.
 */

public class Message {

    /**
     * Nom de l'utilisateur ayant envoyé le emssage
     */
    public String userName;

    /**
     * Contenu du message
     */
    public String content;

    /**
     * Email de l'utilisateur ayant envoyé le message
     */
    public String userEmail;

    /**
     * Instant temporel précis de l'envoi du message
     */
    public Long timestamp;

    /**
     * Reference de ce message dans la base de données
     */
    public DatabaseReference refDatabase;

    /**
     * Prefix du lien pour un gravatar
     */

    public static String GRAVTAR_PREFIX = "https://www.gravatar.com/avatar/";

    public Message() {}

    public Message(String content,String userName,String userEmail,Long timestamp) {
        this.userName=userName;
        this.content=content;
        this.userEmail=userEmail;
        this.timestamp=timestamp;
    }

    /**
     * Permet de determiner si ce message a été envoyé par l'utilisateur actuel du système
     * @return true si l'utilisateur a envoyé le emssage, false sinon
     */

    public boolean sendFromCurrentUser() {
        // On sa base sur l'email contenu dans le système pour déterminer cela (et pas sur le nom)
        return (userEmail!=null && userEmail.equals(UserStorage.getEmail()));
    }

    /**
     * Permet d'obtenir une représentation textuelle relative de l'instant d'envoi
     * @return Le moment d'envoi décrit relativement
     */
    public String getTimeInformations() {
        Date sended = new Date(timestamp); //Date d'envoi
        Date now = new Date(); //Date actuelle
        long diffTime = now.getTime()-sended.getTime(); //Difference de temps
        int secondes = (int) TimeUnit.MILLISECONDS.toSeconds(diffTime);
        String pluriel; //Va permettre d'afficher le "s" ou non selon la plurialité de la phrase
        /*
        La logique est la suivante : on regarde si la durée de l'unité traité est inférieue à la quantité qui la ferait passer à l'unité supérieure
        Par exemple, pour les secondes, 60, ce qui ferait passer aux minutes, pour les heures, 24, si qui ferait apsser en jours...
        Si on est dans la tranche correspondante, on définit alors la phrase
         */
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
        return "(Il y a plus d'un an)"; //On ne compte pas les années
    }
}
