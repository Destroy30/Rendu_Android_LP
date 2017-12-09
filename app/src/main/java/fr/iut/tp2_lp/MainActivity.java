package fr.iut.tp2_lp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ValueEventListener{

    private RecyclerView recycle;
    private MessageAdapter mAdapter;
    private EditorImage sendMessage;
    private ImageButton sendButton;
    private ProgressBar chatLoading;
    private TextView messageLoading;
    private static DatabaseReference mDatabaseRefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserStorage.getUserInfo(this);
        if(UserStorage.getUserName()==null || UserStorage.getEmail()==null) {
            launchDataPicker();
            finish();
        }
        setContentView(R.layout.activity_main);
        this.recycle=(RecyclerView) findViewById(R.id.recyclerView);
        this.sendMessage = (EditorImage) findViewById(R.id.inputEditText);
        this.sendMessage.setChat(this);
        this.sendButton = (ImageButton) findViewById(R.id.sendButton);
        this.chatLoading = (ProgressBar) findViewById(R.id.progressBarChat);
        this.chatLoading.setVisibility(View.VISIBLE);
        this.messageLoading = (TextView) findViewById(R.id.progressBarMessage);
        this.messageLoading.setVisibility(View.VISIBLE);
        List<Message> datas=new ArrayList<Message>();
        this.mAdapter = new MessageAdapter(datas);
        this.recycle.setAdapter(this.mAdapter);
        LinearLayoutManager layoutRecycle = new LinearLayoutManager(this);
        layoutRecycle.setStackFromEnd(true);
        this.recycle.setLayoutManager(layoutRecycle);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseRefrence = database.getReference("chat/messages");
        mDatabaseRefrence.addValueEventListener(this);
        this.sendButton.setOnClickListener(new Button.OnClickListener () {
            @Override
            public void onClick(View v) {
                send(sendMessage.getText().toString());
                sendMessage.setText("");
            }
        });
    }

    public void send(String content) {
        DatabaseReference newDbRef = mDatabaseRefrence.push();
        Date now = new Date();
        newDbRef.setValue(new Message(content,UserStorage.getUserName(),UserStorage.getEmail(),now.getTime()));
        sendMessage.requestFocus();
        recycle.smoothScrollToPosition(mAdapter.getItemCount());
    }

     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                UserStorage.removeUserInfo(this);
                launchDataPicker();
                return true;
            case R.id.action_gravtar:
                String url = "https://fr.gravatar.com/site/signup/";
                Intent gravatarCreation = new Intent(Intent.ACTION_VIEW);
                gravatarCreation.setData(Uri.parse(url));
                startActivity(gravatarCreation);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Message> items = new ArrayList<>();
        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Message messageToAdd = postSnapshot.getValue(Message.class);
            messageToAdd.refDatabase=postSnapshot.getRef();
            items.add(messageToAdd);
        }
        this.mAdapter.setData(items);
        this.chatLoading.setVisibility(View.GONE);
        this.messageLoading.setVisibility(View.GONE);
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void launchDataPicker() {
        Intent intent = new Intent(this, NamePickerActivity.class);
        startActivity(intent);
    }


}
