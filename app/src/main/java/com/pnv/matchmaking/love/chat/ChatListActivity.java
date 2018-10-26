package com.pnv.matchmaking.love.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pnv.matchmaking.love.R;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {

    private static final String TAG = ChatDetailActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private RecyclerView recycler_view_chat_list;


    private String messagesName = "messages";

    CustomRecyclerAdapterChatList customRecyclerAdapterChatList;

    ArrayList<Message> arr_list_message = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        recycler_view_chat_list = (RecyclerView) findViewById(R.id.recycler_view_chat_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatListActivity.this);
        recycler_view_chat_list.setLayoutManager(layoutManager);
        recycler_view_chat_list.setItemAnimator(new DefaultItemAnimator());
        recycler_view_chat_list.setNestedScrollingEnabled(false);
        recycler_view_chat_list.setHasFixedSize(false);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference(messagesName);

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr_list_message.clear();
                for (final DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = data.getKey();
                    mFirebaseDatabase.child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ArrayList<Message> arr_message = new ArrayList<>();

                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Message message = d.getValue(Message.class);
                                arr_message.add(message);
                            }

                            arr_list_message.add(arr_message.get(arr_message.size() - 1));
                            arr_message.clear();
                            recycler_view_chat_list.setAdapter(customRecyclerAdapterChatList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // ...
            }
        });

        customRecyclerAdapterChatList = new CustomRecyclerAdapterChatList(arr_list_message, this);

    }

}
