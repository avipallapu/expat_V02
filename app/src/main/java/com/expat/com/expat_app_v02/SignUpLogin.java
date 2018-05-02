package com.expat.com.expat_app_v02;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpLogin extends AppCompatActivity {

    private Boolean loginModeActive = false;
    private Boolean isUserAuthenticated = false;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    public void redirectIfLoggedIn() {
        if (isUserAuthenticated) {
            if (loginModeActive) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), SetupPassword.class);
                startActivity(intent);
            }
        }
    }

    public void toggleLoginMode(View view) {
        EditText employeeIDEditText = findViewById(R.id.employeeIDEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText otpEditText = findViewById(R.id.otpEditText);

        Button loginSignupButton = findViewById(R.id.loginSignupButton);
        TextView toggleLoginModeTextView = findViewById(R.id.toggleLoginModeTextView);
        TextView resendOTPTextView = findViewById(R.id.resendOTPTextView);
        if (loginModeActive) {
            loginModeActive = false;
            loginSignupButton.setText("Create Account");
            toggleLoginModeTextView.setText("Or, log in");
            employeeIDEditText.setVisibility(View.VISIBLE);
            otpEditText.setVisibility(View.VISIBLE);
            resendOTPTextView.setVisibility(View.VISIBLE);
            passwordEditText.setVisibility(View.INVISIBLE);
        } else {
            loginModeActive = true;
            loginSignupButton.setText("Log In");
            toggleLoginModeTextView.setText("Or, create account");
            employeeIDEditText.setVisibility(View.INVISIBLE);
            otpEditText.setVisibility(View.INVISIBLE);
            resendOTPTextView.setVisibility(View.INVISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);
        }
    }

    public void signupLogin(View view) {
        EditText employeeIDEditText = findViewById(R.id.employeeIDEditText);
        EditText otpEditText = findViewById(R.id.otpEditText);

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        Log.i("info", emailEditText.getText().toString());
        Log.i("info", otpEditText.getText().toString());

        if (loginModeActive) {
            firebaseAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, (task) -> {
                        if (task.isSuccessful()) {
                            isUserAuthenticated = true;
                            Toast.makeText(SignUpLogin.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            isUserAuthenticated = false;
                            Toast.makeText(SignUpLogin.this, "Login credentials invalid", Toast.LENGTH_SHORT).show();
                        }
                    });
            redirectIfLoggedIn();
        } else {
            if (TextUtils.isEmpty(emailEditText.getText())) {
                Log.i("warn", "Empty username here" + emailEditText.getText().toString());
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT);
                return;
            }
            if (TextUtils.isEmpty(otpEditText.getText())) {
                Log.i("warn", "Empty password here" + otpEditText.getText().toString());
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT);
                return;
            }

            /*progressDialog.setMessage("Registering User...");
            progressDialog.show();*/


            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        //If email exists then toast shows else store the data on new key
                        for (DataSnapshot d : data.getChildren()) {
                            if (d.getValue().equals(emailEditText.getText().toString())) {
                                Toast.makeText(SignUpLogin.this, "E-mail is present there", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpLogin.this, "E-mail is not recognized, please contact customer care", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(final DatabaseError databaseError) {
                }
            });


/*            firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), otpEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupLoginActivity.this, "User is registered successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignupLoginActivity.this, "Could not register user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            redirectIfLoggedIn();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);
        setTitle("Expat Login");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        redirectIfLoggedIn();
    }
}
