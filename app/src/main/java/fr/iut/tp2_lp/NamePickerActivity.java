package fr.iut.tp2_lp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

/**
 * Created by destr_000 on 04/12/2017.
 */

public class NamePickerActivity extends AppCompatActivity {

    public static final String TAG = NamePickerActivity.class.getSimpleName();

    EditText    mNameEditText;
    EditText mEmailEditText;
    Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namepicker);
        mNameEditText=(EditText) findViewById(R.id.nameText);
        mEmailEditText=(EditText) findViewById(R.id.emailText);
        autoGetEmail();
        mEmailEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String nameValue=mNameEditText.getText().toString().trim();
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && nameValue.length()>0) {
                    pickedGoStorage();
                    return true;
                }
                return false;
            }
        });
        mSubmitButton=(Button) findViewById(R.id.buttonPicker);
        mSubmitButton.setOnClickListener(new Button.OnClickListener () {
            @Override
            public void onClick(View v) {
                pickedGoStorage();
            }
        });
    }

    public void pickedGoStorage() {
        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        UserStorage.setUserDatas(name,email);
        UserStorage.saveUserInfo(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void autoGetEmail() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        try {
            Account[] accounts = AccountManager.get(this.getApplicationContext()).getAccounts();
            if(accounts.length>0 && emailPattern.matcher(accounts[0].name).matches()) {
                mEmailEditText.setText(accounts[0].name);
            }
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }



}
