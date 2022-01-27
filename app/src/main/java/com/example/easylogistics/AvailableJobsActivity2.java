package com.example.easylogistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AvailableJobsActivity2 extends AppCompatActivity {
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_jobs2);

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("JobDatabase").whereEqualTo("available", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String jobID;
                        jobID = document.getString("jobID");
                        setup_textview(jobID);
                    }
                }
            }
        });
    }

    private void setup_textview(String jobID) {
        LinearLayout layout = findViewById(R.id.availableJobs_layout);
        TextView new_text = new TextView(this);
        new_text.setText(jobID);
        layout.addView(new_text);
    }
}