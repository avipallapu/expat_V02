package com.expat.com.expat_app_v02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Dashboard extends AppCompatActivity {

    private VideoView videoView;
    private TextView dashboardDesc;
    private String userEmail;
    private String video_url="https://www.youtube.com/results?search_query=installing+kylo";
    private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");

        if (mediaControls == null) {
            mediaControls = new MediaController(Dashboard.this);
        }


        videoView =  findViewById(R.id.dashboardVideoView);
        dashboardDesc = findViewById(R.id.dashboardDescTextView);
    }

    @Override
    public void onBackPressed() {
        if(!userEmail.isEmpty()){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing activity")
                    .setMessage("Are you sure you want to logout")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Dashboard.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}
