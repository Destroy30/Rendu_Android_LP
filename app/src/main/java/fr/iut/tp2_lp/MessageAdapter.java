package fr.iut.tp2_lp;

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

/**
 * Created by destr_000 on 27/11/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {

    private List<Message> messages;

    public MessageAdapter(List data) {
        super();
        this.messages=data;
    }

    public void setDataList(List data) {
        this.messages=data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private ImageView userImage;
        private ImageView sendedImage;
        private TextView message;
        private DatabaseReference messageRef;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.card = (CardView) itemView.findViewById(R.id.cardRow);
            this.card.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View arg0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(card.getContext());
                    builder.setMessage(R.string.deleteCard)
                            .setTitle(R.string.deleteTitle);
                    builder.setPositiveButton(R.string.validerDelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            messageRef.removeValue();
                        }
                    });
                    builder.setNegativeButton(R.string.invaliderDelete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;

                }
            });
            this.userImage = (ImageView) itemView.findViewById(R.id.TV_1);
            this.sendedImage = (ImageView) itemView.findViewById(R.id.sendImage);
            this.message = (TextView) itemView.findViewById(R.id.TV_2);
        }

        public void setData(Message message) {
            this.messageRef=message.refDatabase;
            sendedImage.setImageDrawable(null);
            String content = message.content;
            String contentType=Utils.getMimeType(content);
            if(contentType!=null && (contentType.equals("image/gif") || contentType.equals("image/png"))) {
                formatImageMessage(message);
            }
            else {
                formatNormalMessage(message);
            }
            formatUserIcon(message.userEmail);
        }

        private void formatUserIcon(String email) {
            Glide.with(userImage.getContext())
                    .load(Message.GRAVTAR_PREFIX+Utils.md5(email))
                    .apply(RequestOptions.circleCropTransform())
                    .into(userImage);
        }

        private void formatNormalMessage(Message message) {
            this.message.setText(message.userName+ " : \n"+message.content+"\n\n"+message.getTimeInformations());
        }

        private void formatImageMessage(Message message) {
            this.message.setText(message.userName+" "+message.getTimeInformations()+" : ");
            Glide.with(sendedImage.getContext())
                    .load(message.content)
                    .apply(RequestOptions.overrideOf(500,500))
                    .into(sendedImage);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_user, parent, false);
        }
        else {
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

    @Override
    public int getItemViewType(int position) {
        return this.messages.get(position).sendFromCurrentUser() ? 1 : 0;
    }
}
