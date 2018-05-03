package com.expat.com.expat_app_v02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SetupPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_password);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");

    }
}
