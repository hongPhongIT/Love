package com.pnv.matchmaking.love.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pnv.matchmaking.love.R;

import java.util.List;

public class CustomRecyclerAdapterChatList extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Message> list_messages;
    Context context;

    public CustomRecyclerAdapterChatList(List<Message> messages, Context context) {
        this.list_messages = messages;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_message,parent,false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        String a = list_messages.get(position).getMessageUser();
        holder.text_view_user_name.setText(list_messages.get(position).getMessageUser());
        holder.text_view_content.setText(list_messages.get(position).getMessageText());
        holder.text_view_time.setText(list_messages.get(position).getMessageTime());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick) {
                    redirect(position);
                    Toast.makeText(context, "Long Click: " + list_messages.get(position), Toast.LENGTH_SHORT).show();
                }else
                {
                    redirect(position);
                    Toast.makeText(context, " "+ list_messages.get(position), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_messages.size();
    }

    private void redirect(int position){
        Intent intent = new Intent(this.context, ChatDetailActivity.class);
        intent.putExtra("chatName",list_messages.get(position).getMessageUser());
        this.context.startActivity(intent);
    }
}

