package com.pnv.matchmaking.love.profile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pnv.matchmaking.love.R;

import java.util.HashMap;
import java.util.Map;


public class EditProfile extends AppCompatActivity {

    DatabaseReference reference;
    ValueEventListener userListener;
    FirebaseAuth auth;
    String userKey, username, intro;

    EditText edtUsername, edtIntro;
    TextView viewEmail;
    Button updateProfile, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth = FirebaseAuth.getInstance();
        userKey = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        updateProfile = (Button) findViewById(R.id.update_profile);
        btnBack = (Button) findViewById(R.id.btn_back);
        edtUsername = (EditText) findViewById(R.id.username);
        edtIntro = (EditText) findViewById(R.id.intro);

        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // [START_EXCLUDE]
                    edtUsername.setText(user.username);
                    viewEmail.setText(user.email);
                    edtIntro.setText(user.intro);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Edit profile", "loadProfile:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(EditProfile.this, "Failed to edit profile.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            username = edtUsername.getText().toString().trim();
                            intro = edtIntro.getText().toString().trim();

                            if (TextUtils.isEmpty(username)) {
                                Toast.makeText(getApplicationContext(), "Username is required!", Toast.LENGTH_SHORT).show();
                                return;
                            }
//                            updateProfile(userKey,username,intro);
                            startActivity(new Intent(EditProfile.this, Profile.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateProfile(String userKey, String username, String intro) {
        String key = reference.child("users").push().getKey();
        User user = new User(userKey, username, intro);
        Map<String, Object> userValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + key, userValues);
        childUpdates.put("/user-posts/"  + key, userValues);

        reference.updateChildren(childUpdates);
    }
}
