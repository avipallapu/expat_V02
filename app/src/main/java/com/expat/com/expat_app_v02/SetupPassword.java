package com.expat.com.expat_app_v02;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SetupPassword extends AppCompatActivity {

    private Boolean isUserRegistered = false;
    private FirebaseAuth firebaseAuth;
    private String userEmail;

    public void setupPassword(View view) {

        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText renterPasswordEditText = findViewById(R.id.renterPasswordEditText);

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            Log.i("warn", "Empty password here" + passwordEditText);
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(renterPasswordEditText.getText().toString())) {
            Log.i("warn", "Empty password here" + renterPasswordEditText);
            Toast.makeText(this, "Please re-enter valid password", Toast.LENGTH_SHORT);
            return;
        }

        if (passwordEditText.getText().toString().equals(renterPasswordEditText.getText().toString())) {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SetupPassword.this, "User is registered successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SetupPassword.this, "Could not register user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(SetupPassword.this, "Passwords should match, please check again!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_password);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");

        firebaseAuth = FirebaseAuth.getInstance();

    }
}
