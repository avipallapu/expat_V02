package com.expat.com.expat_app_v02;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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

    private void redirectIfLoggedIn(String emailEditText) {
        if (isUserAuthenticated) {
            if (loginModeActive) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                intent.putExtra("userEmail", emailEditText);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), SetupPassword.class);
                intent.putExtra("userEmail", emailEditText);
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
        Editable employeeIDEditText = ((EditText) findViewById(R.id.employeeIDEditText)).getText();
        Editable otpEditText = ((EditText) findViewById(R.id.otpEditText)).getText();
        Editable emailEditText = ((EditText) findViewById(R.id.emailEditText)).getText();
        Editable passwordEditText = ((EditText) findViewById(R.id.passwordEditText)).getText();

        if (loginModeActive) {
            firebaseAuth.signInWithEmailAndPassword(emailEditText.toString(), passwordEditText.toString())
                    .addOnCompleteListener(this, (task) -> {
                        if (task.isSuccessful()) {
                            isUserAuthenticated = true;
                            Toast.makeText(SignUpLogin.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                            redirectIfLoggedIn(emailEditText.toString());
                        } else {
                            isUserAuthenticated = false;
                            Toast.makeText(SignUpLogin.this, "Login credentials invalid", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            if (TextUtils.isEmpty(emailEditText)) {
                Log.i("warn", "Empty username here" + emailEditText);
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT);
                return;
            }
            if (TextUtils.isEmpty(otpEditText)) {
                Log.i("warn", "Empty password here" + otpEditText);
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT);
                return;
            }

            /*progressDialog.setMessage("Registering User...");
            progressDialog.show();*/


            DatabaseReference signUsers = mDatabase.getDatabase().getReference("signUsers");
            signUsers.orderByChild("email").startAt(emailEditText.toString()).endAt(emailEditText.toString())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String email = ds.child("email").getValue(String.class);
                                Long otp = ds.child("otp").getValue(Long.class);
                                String empId = ds.child("empId").getValue(String.class);
                                if (otp.equals(Long.parseLong(otpEditText.toString())) && empId.equals(employeeIDEditText.toString())) {
                                    isUserAuthenticated = true;
                                    redirectIfLoggedIn(emailEditText.toString());
                                } else {
                                    Toast.makeText(SignUpLogin.this, "Invalid email/Otp", Toast.LENGTH_SHORT).show();
                                    emailEditText.clear();
                                    otpEditText.clear();
                                    employeeIDEditText.clear();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(SignUpLogin.this, "Error with App database connection", Toast.LENGTH_SHORT).show();
                        }
                    });
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
        String emailEditText = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        redirectIfLoggedIn(emailEditText);
    }
}
