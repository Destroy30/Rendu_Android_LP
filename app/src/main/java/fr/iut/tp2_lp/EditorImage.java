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
 * Created by destr_000 on 08/12/2017.
 */

public class EditorImage extends EditText {

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

    public void setChat(MainActivity chat) {
        this.chat=chat;
    }


    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        final InputConnection ic = super.onCreateInputConnection(editorInfo);
        EditorInfoCompat.setContentMimeTypes(editorInfo,
                new String [] {"image/png","image/gif"});

        final InputConnectionCompat.OnCommitContentListener callback =
                new InputConnectionCompat.OnCommitContentListener() {
                    @Override
                    public boolean onCommitContent(InputContentInfoCompat inputContentInfo,int flags, Bundle opts) {
                        if (BuildCompat.isAtLeastNMR1() && (flags &
                                InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION) != 0) {
                            try {
                                inputContentInfo.requestPermission();
                                chat.send(inputContentInfo.getLinkUri().toString());
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
