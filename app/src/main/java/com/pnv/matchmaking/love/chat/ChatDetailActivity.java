package com.pnv.matchmaking.love.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;

    private String messageID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtMessage = (TextView) findViewById(R.id.txt_message);
        inputMessage = (EditText) findViewById(R.id.edit_message);
        btnSave = (Button) findViewById(R.id.btn_save);

        mAuth = FirebaseAuth.getInstance();

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputMessage.getText().toString();

                // Check for already existed userId
                if (TextUtils.isEmpty(message)) {
                    createMessage(message);
                }

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    /**
     * Creating new user node under 'users'
     */
    private void createMessage(String message) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(messageID)) {
            messageID = mFirebaseDatabase.push().getKey();
        }

//        Message message = new Message(message, b);
//
//        mFirebaseDatabase.child(userId).setValue(user);
//
//        addUserChangeListener();
    }

    /**
     * User data change listener
     */
//    private void addUserChangeListener() {
//        // User data change listener
//        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//
//                // Check for null
//                if (user == null) {
//                    Log.e(TAG, "User data is null!");
//                    return;
//                }
//
//                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);
//
//                // Display newly updated name and email
//                txtDetails.setText(user.name + ", " + user.email);
//
//                // clear edit text
//                inputEmail.setText("");
//                inputName.setText("");
//
//                toggleButton();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read user", error.toException());
//            }
//        });
//    }
//
//    private void updateUser(String name, String email) {
//        // updating the user via child nodes
//        if (!TextUtils.isEmpty(name))
//            mFirebaseDatabase.child(userId).child("name").setValue(name);
//
//        if (!TextUtils.isEmpty(email))
//            mFirebaseDatabase.child(userId).child("email").setValue(email);
//    }
}
