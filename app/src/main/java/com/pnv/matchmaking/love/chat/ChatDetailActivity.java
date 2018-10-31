package com.pnv.matchmaking.love.chat;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pnv.matchmaking.love.Login;
import com.pnv.matchmaking.love.R;

import java.util.ArrayList;
import java.util.Collections;

public class ChatDetailActivity extends AppCompatActivity {

    private static final String TAG = ChatDetailActivity.class.getSimpleName();
    private RecyclerView recycler_view_messages;
    private EditText inputMessage;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mFirebaseAuth;


    private String messagesName = "messages";
    private String userEmail;

    CustomRecyclerAdapterMessage customRecyclerAdapterMessage;
    ArrayList<Message> arr_message = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        recycler_view_messages = (RecyclerView) findViewById(R.id.recycler_view_messages);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatDetailActivity.this);
        recycler_view_messages.setLayoutManager(layoutManager);
        recycler_view_messages.setItemAnimator(new DefaultItemAnimator());
        recycler_view_messages.setNestedScrollingEnabled(false);
        recycler_view_messages.setHasFixedSize(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        inputMessage = (EditText) findViewById(R.id.edit_message);
        btnSave = (Button) findViewById(R.id.btn_save);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference(messagesName);

        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        mFirebaseAuth = FirebaseAuth.getInstance();

        final String  currentUserKey = mFirebaseAuth.getCurrentUser().getUid();
        userEmail = mFirebaseAuth.getCurrentUser().getEmail();

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

        mFirebaseDatabase.child(getKeyMessage()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr_message.clear();
                for (final DataSnapshot data : dataSnapshot.getChildren()) {
                    Message message = data.getValue(Message.class);
                    arr_message.add(message);
                }

                Collections.reverse(arr_message);
                recycler_view_messages.setAdapter(customRecyclerAdapterMessage);
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
                createMessage(message, getKeyMessage());
            }
        });

        customRecyclerAdapterMessage = new CustomRecyclerAdapterMessage(arr_message, this);

    }


    private void createMessage(String message, String keyMessage) {
        if (keyMessage != null) {
            String email = userEmail;
            Message m = new Message(message, email);
            mFirebaseDatabase.child(keyMessage).child(mFirebaseDatabase.push().getKey()).setValue(m);
        } else {
        }
    }

    private String getKeyMessage(){
        Bundle extras = this.getIntent().getExtras();
        String keyMessage = extras.getString("chatKey");
        return keyMessage;
    }

}
