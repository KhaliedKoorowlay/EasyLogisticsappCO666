package com.example.easylogistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class CurrentJobActivity extends AppCompatActivity {

    //Initializing variables
    TextView tv_startTime;
    TextView tv_pickUpLocation;
    TextView tv_dropOffPoint;
    TextView tv_vehicleRegistration;
    TextView tv_JobId;
    TextView tv_TrailerId;
    TextView tv_FilmProjectName;
    Button btn_jobCompleted;
    Button btn_BackToMain;
    String currentJobID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job);

        //Assigning values to variables
        tv_startTime = findViewById(R.id.textViewStartTime);
        tv_pickUpLocation = findViewById(R.id.textViewPickUpLocation);
        tv_dropOffPoint = findViewById(R.id.textViewDropOffPoint);
        tv_vehicleRegistration = findViewById(R.id.textViewVehicleRegistration);
        tv_JobId = findViewById(R.id.textViewJobID);
        tv_TrailerId = findViewById(R.id.textViewTrailerId);
        tv_FilmProjectName = findViewById(R.id.textViewFilmProjectName);
        btn_jobCompleted = findViewById(R.id.buttonJobCompleted);
        btn_BackToMain = findViewById(R.id.buttonBackToMain);

        setUpData();

        //Back to main button
        btn_BackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    // Getting data displayed from Firebase on the activity
    private void setUpData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Getting the current job ID from UserDatabase
        db.collection("UserDatabase").document(user.getUid()).
                get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        currentJobID = task.getResult().getString("currentJobID");
                        getJob(currentJobID, db);
                    }
                });
    }

    // Getting job details from JobDatabase based on jobID
    private void getJob(String jobID, FirebaseFirestore db) {
        db.collection("JobDatabase").document(jobID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        tv_startTime.setText(task.getResult().getString("startTime"));
                        tv_pickUpLocation.setText(task.getResult().getString("pickUpLocation"));
                        tv_dropOffPoint.setText(task.getResult().getString("dropOffLocation"));
                        tv_vehicleRegistration.setText(task.getResult().getString("vehicleRegistration"));
                        tv_JobId.setText(task.getResult().getString("jobID"));
                        tv_TrailerId.setText(task.getResult().getString("trailerID"));
                        tv_FilmProjectName.setText(task.getResult().getString("filmProjectName"));

                        //Job completed button
                        btn_jobCompleted.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> data = new HashMap<>();
                                data.put("jobCompleted", true);
                                data.put("jobID", "none");

                                db.collection("JobDatabase").document(jobID).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(CurrentJobActivity.this, "Job completed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
    }
}