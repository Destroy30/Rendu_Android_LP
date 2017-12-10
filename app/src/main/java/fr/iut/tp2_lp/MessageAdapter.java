package fr.iut.tp2_lp;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {

    /**
     * Liste des différents messages cotenus dans l'adapter
     */
    private List<Message> messages;

    public MessageAdapter(List data) {
        super();
        this.messages=data;
    }

    public void setDataList(List data) {
        this.messages=data;
    }

    /**
     * Classe gérant l'affichage d'un message
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Carte view, cette 'carte' va contenir l'ensemble des données du message
         */
        private CardView card;

        /**
         * Avatar de l'utilisateur ayant envoyé le message
         */
        private ImageView userImage;

        /**
         * Eventuelle image envoyée par le clavier (gif,png...)
         */
        private ImageView sendedImage;

        /**
         * Contenu du message envoyé
         */
        private TextView message;

        /**
         * Reference de ce message dans la base de données (permettra notament de l'effacer plus tard)
         */
        private DatabaseReference messageRef;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.card = (CardView) itemView.findViewById(R.id.cardRow);
            //Definition d'une action (supression) lors du "clicc long"
            this.card.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View arg0) {
                    //Affichage d'une boite de dialogue pour confirmation
                    AlertDialog.Builder builder = new AlertDialog.Builder(card.getContext());
                    builder.setMessage(R.string.deleteCard)
                            .setTitle(R.string.deleteTitle);
                    builder.setPositiveButton(R.string.validerDelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Si il confirme, on supprime le message
                            messageRef.removeValue();
                        }
                    });
                    builder.setNegativeButton(R.string.invaliderDelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {} //Sinon on ne fait rien
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show(); //Affichage de la boite de dialogue
                    return true;

                }
            });
            this.userImage = (ImageView) itemView.findViewById(R.id.TV_1);
            this.sendedImage = (ImageView) itemView.findViewById(R.id.sendImage);
            this.message = (TextView) itemView.findViewById(R.id.TV_2);
        }

        /**
         * Cette methode va permettre d'afficher les données du message sur l'interface
         * @param message
         */
        public void setData(Message message) {
            this.messageRef=message.refDatabase;
            sendedImage.setImageDrawable(null); //Vidage de la zone d'image (gif,png..)
            String content = message.content;
            String contentType=Utils.getMimeType(content); //Recupération d'un cotenu image eventuel
            if(contentType!=null && (contentType.equals("image/gif") || contentType.equals("image/png"))) {
                //Si le contenu est de type gif ou png, alors on affiche cette image pour contenu
                formatImageMessage(message);
            }
            else {
                //Sinon, on affiche le message normalement
                formatNormalMessage(message);
            }
            //Enfin, on affiche l'avatar de l'exéditeur
            formatUserIcon(message.userEmail);
        }

        /**
         * Permet d'afficher un avatar à partir d'un email
         * @param email email de l'expediteur
         */
        private void formatUserIcon(String email) {
            if(!((Activity)userImage.getContext()).isFinishing()) { //Pour ne pas déclencher d'erreur lors de la reconnexion
                Glide.with(userImage.getContext())
                        .load(Message.GRAVTAR_PREFIX + Utils.md5(email)) //Charement d'un lien gravtar, que glide affiche
                        .apply(RequestOptions.circleCropTransform())
                        .into(userImage);
            }
        }

        /**
         * Affichage d'un message classique (texte)
         * @param message
         */
        private void formatNormalMessage(Message message) {
            this.message.setText(message.userName+ " : \n"+message.content+"\n\n"+message.getTimeInformations());
        }

        /**
         * Affichage d'une image comme message
         * @param message
         */
        private void formatImageMessage(Message message) {
            this.message.setText(message.userName+" "+message.getTimeInformations()+" : ");
            if(!((Activity)sendedImage.getContext()).isFinishing()) {
                Glide.with(sendedImage.getContext())
                        .load(message.content) //Le content est un lien (d'une image)
                        .apply(RequestOptions.overrideOf(500, 500)) //redimensionnement
                        .into(sendedImage);
            }
        }

    }

    /**
     * Methode executée à la creation d'un viewholder, elle va choisir le bon layout
     * @param parent
     * @param viewType Type de vue (envoyé par l'utilisateur courant ou un autre utilisateur...)
     * @return Le view holder concerné
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==1) { //Cela signifie que le message a été envoyé par l'utilisateur
            //On charge un layout spécifique (plaçant le message à droite)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_user, parent, false);
        }
        else { //Le message a été envoyé par un autre utilisateur
            //On charge un layout spécifique (plaçant le message à gauche)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(this.messages.get(position));
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    public void setData(List<Message>messages) {
        this.messages=messages;
        this.notifyDataSetChanged();
    }

    /**
     * Permet de déterminer si un message à été envoyé par l'utilisateur actuel
     * @param position : Position du message dans la liste des messages
     * @return numero definissant le type de vue à appeller
     */
    @Override
    public int getItemViewType(int position) {
        return this.messages.get(position).sendFromCurrentUser() ? 1 : 0;
    }
}
