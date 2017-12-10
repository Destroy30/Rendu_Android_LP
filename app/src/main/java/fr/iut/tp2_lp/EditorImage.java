package fr.iut.tp2_lp;

import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v4.os.BuildCompat;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * Cette classe étend EditText et va permettre de prendre en charge les claviers envoyant des images png ou gif
 * Attention toutefois, cette classe et ses méthodes ne seont prise en compte que sur l'API 25 (autrement, rien ne se passera cocnrètmeent à l'envoi d'une image)
 */
public class EditorImage extends EditText {

    /**
     * Activité principale (utile pour délcnher la méthode d'envoi : send)
     */
    private MainActivity chat;


    public EditorImage(Context context) {
        super(context);
    }

    public EditorImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public EditorImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Permet de lier l'activité principal à cet editor
     * @param chat : Activité principale (le chat)
     */
    public void setChat(MainActivity chat) {
        this.chat=chat;
    }


    /**
     * Cette méthode va être déclcnehcée lors de l'envoi d'un contenu particulier par le clavier
     * Si celui ci est reconnu comme image (png ou gif dans notre cas) on l'envoi
     * @param editorInfo
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        final InputConnection ic = super.onCreateInputConnection(editorInfo);
        EditorInfoCompat.setContentMimeTypes(editorInfo,
                new String [] {"image/png","image/gif"}); //Formats autorisés

        //lors de l'envoi (clic sur l'élément..)
        final InputConnectionCompat.OnCommitContentListener callback =
                new InputConnectionCompat.OnCommitContentListener() {
                    @Override
                    public boolean onCommitContent(InputContentInfoCompat inputContentInfo,int flags, Bundle opts) {
                        if (BuildCompat.isAtLeastNMR1() && (flags &
                                InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0) {
                            try {
                                inputContentInfo.requestPermission(); //On verifie les permissions
                                chat.send(inputContentInfo.getLinkUri().toString()); //On envoi le lien comme message
                                //Ce lien sera intérpêté et convertit en image par l'application quand elle reçevra le message
                            }
                            catch (Exception e) {
                                return false;
                            }
                        }

                        return true;
                    }
                };
        return InputConnectionCompat.createWrapper(ic, editorInfo, callback);
    }


}
