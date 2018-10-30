package com.pnv.matchmaking.love.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pnv.matchmaking.love.R;
import com.pnv.matchmaking.love.User;

import java.util.List;

public class CustomRecyclerAdapterUser extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<User> list_user;
    Context context;
    LayoutInflater inflater;
    private FirebaseAuth mFirebaseAuth;

    public CustomRecyclerAdapterUser(List<User> users, Context context) {
        this.list_user = users;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.user, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.txt_name.setText(list_user.get(position).getUsername());
        holder.btn_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth = FirebaseAuth.getInstance();
                String currentUserKey = mFirebaseAuth.getCurrentUser().getUid();
                String keyMyFriend = list_user.get(position).getKey();
                String keyMessage = currentUserKey + "MAP" + keyMyFriend;
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("chatKey", keyMessage);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_user.size();
    }


}

