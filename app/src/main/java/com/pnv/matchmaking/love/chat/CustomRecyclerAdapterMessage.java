package com.pnv.matchmaking.love.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnv.matchmaking.love.R;

import java.util.List;

public class CustomRecyclerAdapterMessage extends RecyclerView.Adapter<CustomRecyclerAdapterMessage.CustomViewHolder> {

    private List<Message> mMessage;
    Context mContext;
    LayoutInflater inflater;

    public CustomRecyclerAdapterMessage(List<Message> mMessage, Context mContext){
        this.mMessage = mMessage;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public class CustomViewHolder  extends RecyclerView.ViewHolder{

        TextView txt_message;
        TextView txt_time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_message = (TextView) itemView.findViewById(R.id.txt_message);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent,false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        holder.txt_message.setText(mMessage.get(position).getMessageText());
        holder.txt_time.setText(mMessage.get(position).getMessageTime());
    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

}
