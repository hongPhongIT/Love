package com.pnv.matchmaking.love.profile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
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

import java.util.Calendar;

public class Profile extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    FirebaseAuth auth;
    String userKey;

    DatabaseReference userReference;
    FirebaseAuth.AuthStateListener authListener;
    ValueEventListener userListener;

    Toolbar toolbar;
    TextView username, birthYear;
    RelativeLayout changePassword, editProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(Profile.this, Login.class));
            finish();
        }
        userKey = auth.getCurrentUser().getUid();
        if (userKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_USER_KEY");
        }
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(userKey);

        username = (TextView) findViewById(R.id.text_profile_name);
        birthYear = (TextView) findViewById(R.id.text_birth_year);

        editProfile = (RelativeLayout) findViewById(R.id.layout_edit_profile);
        changePassword = (RelativeLayout) findViewById(R.id.layout_change_password);

        Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.getInstance().get(Calendar.YEAR);


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
                    int iBirthYear = Integer.parseInt(user.birthYear);
                    int age = currentYear - iBirthYear;
                    username.setText(user.username);
                    birthYear.setText("Age: " + age);
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

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, EditProfile.class));
            }
        });
    }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            auth.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
