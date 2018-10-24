package com.pnv.matchmaking.love.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pnv.matchmaking.love.R;

import java.util.ArrayList;

public class ChatDetailActivity extends AppCompatActivity {

    private static final String TAG = ChatDetailActivity.class.getSimpleName();
    private RecyclerView recycler_view_messages;
    private EditText inputMessage;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    private String messagesNane = "messages";
    private String userName = "Phong Nguyen";


    RecyclerView recyclerView;
    CustomRecyclerAdapterMessage customRecyclerAdapterMessage;
    ArrayList<Message> arr_message = new ArrayList<>();;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        recycler_view_messages = (RecyclerView) findViewById(R.id.recycler_view_messages);

        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(ChatDetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        inputMessage = (EditText) findViewById(R.id.edit_message);
        btnSave = (Button) findViewById(R.id.btn_save);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference(messagesNane);

        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        mFirebaseDatabase.child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr_message.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // ...
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = inputMessage.getText().toString();
                createMessage(message);
            }
        });

        Query messages = mFirebaseDatabase.child(messagesNane).child(userName);

        customRecyclerAdapterMessage = new CustomRecyclerAdapterMessage(arr_message, this);
        recyclerView.setAdapter(customRecyclerAdapterMessage);
    }


    private void createMessage(String message) {

        String email = userName;
        Message m = new Message(message, email);

        mFirebaseDatabase.child(userName).child(m.getMessageTime()).setValue(m);

    }

}
