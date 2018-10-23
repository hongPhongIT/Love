package com.pnv.matchmaking.love.chat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pnv.matchmaking.love.R;

public class ChatDetailActivity extends AppCompatActivity {

    private static final String TAG = ChatDetailActivity.class.getSimpleName();
    private TextView txtMessage;
    private EditText inputMessage;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String messagesNane = "messages";
    private String userName = "Phong Nguyen";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtMessage = (TextView) findViewById(R.id.txt_message);
        inputMessage = (EditText) findViewById(R.id.edit_message);
        btnSave = (Button) findViewById(R.id.btn_save);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("messages");

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                createchat(userName);

                String message = inputMessage.getText().toString();
                createMessage(message);
            }
        });

    }


    private void createMessage(String message) {


        String email = userName;
        Message m = new Message(message, email);

        mFirebaseDatabase.child(userName).child(m.getMessageTime()).setValue(m);


    }

//    private void createchat(String name) {
//            mFirebaseDatabase.child(userName).setValue(userName);
//    }


}
