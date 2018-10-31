package com.pnv.matchmaking.love.profile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.pnv.matchmaking.love.User;
import com.pnv.matchmaking.love.chat.AllUser;
import com.pnv.matchmaking.love.chat.ChatListActivity;

public class Profile extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    FirebaseAuth auth;
    String userKey;

    DatabaseReference userReference;
    FirebaseAuth.AuthStateListener authListener;

    TextView username, birthYear, txt_friend, txt_message;
    Button btn_logout;
    ValueEventListener userListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        auth = FirebaseAuth.getInstance();

        userKey = auth.getCurrentUser().getUid();
        if (userKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_USER_KEY");
        }
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(userKey);

        username = (TextView) findViewById(R.id.text_profile_name);
        birthYear = (TextView) findViewById(R.id.text_birth_year);

        btn_logout = (Button) findViewById(R.id.logout);
        txt_friend = (TextView) findViewById(R.id.txt_friend);
        txt_message = (TextView) findViewById(R.id.txt_message);
        //get user key from intent


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser authUser = firebaseAuth.getCurrentUser();
                if (authUser == null) {
                    startActivity(new Intent(Profile.this, Login.class));
                    finish();
                }
            }
        };

        // Add value event listener to the user
        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // [START_EXCLUDE]
                if(user!=null){
                    username.setText(user.username);
                    birthYear.setText("Birth year: " + user.birthYear);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadProfile:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(Profile.this, "Failed to load profile.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        txt_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, AllUser.class);
                Profile.this.startActivity(intent);
            }
        });

        txt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ChatListActivity.class);
                Profile.this.startActivity(intent);
            }
        });


        btn_logout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        }));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.action_bar, menu);
//
//        logout = (MenuItem) findViewById(R.id.logout);
//        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                auth.signOut();
//                return true;
//            }
//        });
//        return true;
//    }



    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
        userReference.addValueEventListener(userListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }

    }



}
