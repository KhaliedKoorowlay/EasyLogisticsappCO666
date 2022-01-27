package com.example.easylogistics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AvailableJobsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Initializing variables
    Spinner sp_AvailableJobs;
    Button btn_SelectJob;
    FirebaseFirestore firestore;
    String jobID;
    List<String> docNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_jobs);

        //Assigning values to variables
        sp_AvailableJobs = findViewById(R.id.spinnerAvailableJobs);
        btn_SelectJob = findViewById(R.id.buttonSelectJob);

        setSpinner();

    }

    private void setSpinner() {

        firestore = FirebaseFirestore.getInstance();

        //Setting up array containing all job IDs
        docNames = new ArrayList<String>();
        Task<QuerySnapshot> querySnapshotTask = firestore.collection("JobDatabase").whereEqualTo("available", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        docNames.add(document.getString("jobID"));
                        Log.i("jobIdArray", String.valueOf(docNames));
                    }
                }
            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("one");
        categories.add("two");
        categories.add("three");
        Log.i("dummyArray", String.valueOf(categories));

        //Array adapter for jobID spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, docNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_AvailableJobs.setAdapter(adapter);
        sp_AvailableJobs.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        jobID = parent.getItemAtPosition(position).toString();
        //Debug toast
        Toast.makeText(parent.getContext(), jobID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}