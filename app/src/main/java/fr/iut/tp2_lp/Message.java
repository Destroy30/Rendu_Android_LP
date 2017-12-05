package fr.iut.tp2_lp;

/**
 * Created by destr_000 on 27/11/2017.
 */

public class Message {

    public String userName;
    public String content;
    public String userEmail;
    public Long timestamp;

    public static String GRAVTAR_PREFIX = "https://www.gravatar.com/avatar/";

    public Message() {

    }

    public Message(String content,String userName,String userEmail,Long timestamp) {
        this.userName=userName;
        this.content=content;
        this.userEmail=userEmail;
        this.timestamp=timestamp;
    }
}
