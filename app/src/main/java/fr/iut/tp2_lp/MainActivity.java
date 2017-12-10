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

    /**
     * ReclyclerView contenant les idfférents messages
     */
    private RecyclerView recycle;

    /**
     * L'adapter qui va permettre de gérer l'affichage des messages
     */
    private MessageAdapter mAdapter;

    /**
     * Champ de saisie d'un nouveau message (sur plusieurs lignes)
     */
    private EditorImage sendMessage;

    /**
     * Bouton qui valide l'envoi du message
     */
    private ImageButton sendButton;

    /**
     * Animation affichée lors du chargement du chat (qui peut prendre un certain temps)
     */
    private ProgressBar chatLoading;

    /**
     * Message d'attente affiché lors du chargement du chat
     */
    private TextView messageLoading;

    /**
     * Reference (connexion) avec la firebase contenant les messages
     */
    private static DatabaseReference mDatabaseRefrence;

    /**
     * Initialisiation de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserStorage.getUserInfo(this); //Recuperation des informations utilisateurs enregistrées sur la machine
        if(UserStorage.getUserName()==null || UserStorage.getEmail()==null) { //Si le nom ou l'email ne sont pas définis
            launchDataPicker(); //On l'ance l'activité demandant de saisir les informations pour se connecter
            finish(); //On stoppe l'activité en cours
        }
        setContentView(R.layout.activity_main);
        this.recycle=(RecyclerView) findViewById(R.id.recyclerView);
        this.sendMessage = (EditorImage) findViewById(R.id.inputEditText);
        this.sendMessage.setChat(this);
        this.sendButton = (ImageButton) findViewById(R.id.sendButton);
        this.chatLoading = (ProgressBar) findViewById(R.id.progressBarChat);
        this.chatLoading.setVisibility(View.VISIBLE); //On affiche l'animation de chargement du chat
        this.messageLoading = (TextView) findViewById(R.id.progressBarMessage);
        this.messageLoading.setVisibility(View.VISIBLE); //On affiche le message de chargement du chat
        List<Message> datas=new ArrayList<Message>(); //Liste contenant les messages
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
                //Lors du clic sur le bouton d'envoi, on declenche send et on vide le champ de saisie
                send(sendMessage.getText().toString());
                sendMessage.setText("");
            }
        });
    }

    /**
     * Methode d'envoi d'un message
     * @param content
     */
    public void send(String content) {
        DatabaseReference newDbRef = mDatabaseRefrence.push();
        Date now = new Date(); //Creation de la date actuelle pour le timestamp
        newDbRef.setValue(new Message(content,UserStorage.getUserName(),UserStorage.getEmail(),now.getTime())); //Enregistrement du message
        sendMessage.requestFocus(); //On remet le focus sur le champ de saisie
    }

    /**
     * Definition d'un menu avec deux options (logout et creation d'un gravatar)
     * @param menu
     * @return
     */
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Permet de déclncher une action selon l'option selectionnée dans le menu
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout: //Deconnexion
                UserStorage.removeUserInfo(this);  //Supression des informations enregistrées sur machine
                launchDataPicker(); //Lancement de l'activité pour enregistrer ses informations
                finish();
                return true;
            case R.id.action_gravtar: //Creation d'un gravatar
                String url = "https://fr.gravatar.com/site/signup/"; //Url de création d'un gravatar
                Intent gravatarCreation = new Intent(Intent.ACTION_VIEW); //Lancement du'n intent type URL
                gravatarCreation.setData(Uri.parse(url));
                startActivity(gravatarCreation);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * Cette méthode permet de mettre à jour la liste des messages et l'affichage lorsqu'un ou plusieurs messages sont envoyés
     * Elle fait donc appel à l'adapter
     * @param dataSnapshot
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Message> items = new ArrayList<>(); //Liste des messages
        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            //On enregistre chaque message contenu dans la snapshot
            Message messageToAdd = postSnapshot.getValue(Message.class);
            messageToAdd.refDatabase=postSnapshot.getRef();
            items.add(messageToAdd);
        }
        this.mAdapter.setData(items); //On met à jour l'affichage grâce à l'adpter
        recycle.smoothScrollToPosition(mAdapter.getItemCount()); //On replace le scroll à la fin (pour voir le ou les nouveau(x) message)
        //Si on a affiché les informations de chargement (animation+message, lors du lancement de l'activité) on les fait disparaitre
        this.chatLoading.setVisibility(View.GONE);
        this.messageLoading.setVisibility(View.GONE);
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    /**
     * Methode qui permet de lancer l'activité pour enregistrer les informations
     */

    public void launchDataPicker() {
        Intent intent = new Intent(this, NamePickerActivity.class);
        startActivity(intent);
    }


}
