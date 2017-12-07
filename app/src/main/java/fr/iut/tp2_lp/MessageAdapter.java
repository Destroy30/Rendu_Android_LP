package fr.iut.tp2_lp;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

        private ImageView userImage;
        private TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            this.userImage = (ImageView) itemView.findViewById(R.id.TV_1);
            this.message = (TextView) itemView.findViewById(R.id.TV_2);
        }

        public void setData(Message message) {
            this.message.setText(message.userName+ " : \n"+message.content+"\n\n"+message.getTimeInformations());
            Glide.with(userImage.getContext())
                    .load(Message.GRAVTAR_PREFIX+Utils.md5(message.userEmail))
                    .apply(RequestOptions.circleCropTransform())
                    .into(userImage);
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
