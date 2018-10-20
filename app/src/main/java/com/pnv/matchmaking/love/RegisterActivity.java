package com.pnv.matchmaking.love;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class RegisterActivity extends AppCompatActivity {

    Button buttonRegister;
    EditText editEmail, editPassword, editConfirmPassword;
    TextView textViewErrorEmail, textViewErrorPassword, textViewErrorConfirmPassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        textViewErrorEmail = (TextView) findViewById(R.id.textViewErrorEmail);
        textViewErrorPassword = (TextView) findViewById(R.id.textViewErrorPassword);
        textViewErrorConfirmPassword = (TextView) findViewById(R.id.textViewErrorConfirmPassword);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonRegister) {
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT);
            return;
        }

        progressDialog.setMessage("Register User ...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(RegisterActivity.this, "Could not register ... please try again!", Toast.LENGTH_SHORT).show();
                            Log.e("LoginActivity", "Failed Registration", e);
                        }
                    }
                });
    }
}