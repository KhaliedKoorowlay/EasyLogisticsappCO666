package com.example.easylogistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth authentication;
    FirebaseUser user;
    Button btn_logout;
    Button btn_goToCurrentJob;
    //Delete this if app crashes
    Button btn_availableJobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authentication = FirebaseAuth.getInstance();
        user = authentication.getCurrentUser();
        btn_logout = findViewById(R.id.buttonLogOut);
        btn_goToCurrentJob = findViewById(R.id.buttonCurrentJob);
        //Delete this if app crashes
        btn_availableJobs = findViewById(R.id.buttonAvailableJobs);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentication.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        btn_goToCurrentJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CurrentJobActivity.class));
            }
        });
        //Delete this if app crashes
        btn_availableJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AvailableJobsActivity2.class));

            }
        });
        //Delete until this
    }
}