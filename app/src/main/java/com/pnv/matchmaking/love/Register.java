package com.pnv.matchmaking.love;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pnv.matchmaking.love.profile.Profile;
import com.pnv.matchmaking.love.profile.User;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputPassword, inputBirthYear;
    private Button btnRegister;
    RadioGroup radioGroup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference();

        btnRegister = (Button) findViewById(R.id.btn_register);
        inputUsername = (EditText) findViewById(R.id.username);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputBirthYear = (EditText) findViewById(R.id.birth_year);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        radioGroup = (RadioGroup) findViewById(R.id.gender);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                gender = rb.getText().toString();

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String birthYear = inputBirthYear.getText().toString().trim();


                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.getInstance().get(Calendar.YEAR);

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Username is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email is required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                       Toast.makeText(getApplicationContext(), "Invalid email format!", Toast.LENGTH_SHORT).show();
                       return;
                   }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(birthYear)) {
                    Toast.makeText(getApplicationContext(), "Birth year is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    int iYear = Integer.parseInt(birthYear);
                    if (iYear < (currentYear - 100)|| iYear > (currentYear - 6)){
                        Toast.makeText(getApplicationContext(), "Please enter correct your birth year", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }




                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, "Register succcessful", Toast.LENGTH_SHORT).show();
                                    onAuthSuccess(task.getResult().getUser());
                                    startActivity(new Intent(Register.this, Profile.class));
                                    finish();
                                }
                            }
                        });
            }

            private void onAuthSuccess(FirebaseUser user) {
                createUser(user.getUid(), inputUsername.getText().toString(), user.getEmail(), gender, inputBirthYear.getText().toString());
            }

            private void createUser(String userId, String username, String email, String gender, String birthYear) {
                User user = new User(username, email, gender, birthYear);
                reference.child("users").child(userId).setValue(user);
            }
        });
    }


}