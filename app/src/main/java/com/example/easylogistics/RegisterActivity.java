package com.example.easylogistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText et_first_name;
    EditText et_last_name;
    EditText email_address;
    EditText password;
    Button btn_register;
    Button btn_goToLogin;
    FirebaseAuth authentication;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_first_name = findViewById(R.id.editTextFirstName);
        et_last_name = findViewById(R.id.editTextLastName);
        email_address = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btn_register = findViewById(R.id.buttonRegister);
        btn_goToLogin = findViewById(R.id.buttonGoToLogin);
        authentication = FirebaseAuth.getInstance();

        if (authentication.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first_name = et_first_name.getText().toString().trim();
                String last_name = et_last_name.getText().toString().trim();
                String email = email_address.getText().toString().trim();
                String pwd = password.getText().toString().trim();

                if (first_name.isEmpty()) {
                    et_first_name.setError("First name is required!");
                }

                if (last_name.isEmpty()) {
                    et_last_name.setError("Last name is required!");
                }

                if (email.isEmpty()) {
                    email_address.setError("Email is required!");
                    return;
                }

                if (pwd.isEmpty()) {
                    password.setError("Password is required!");
                }

                if (pwd.length() < 8) {
                    password.setError("8 characters required!");
                }

                authentication.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = authentication.getCurrentUser();

                            firestore = FirebaseFirestore.getInstance();

                            DocumentReference documentReference = firestore.collection("UserDatabase").document(user.getUid());

                            Map<String, Object> userData = new HashMap<>();
                            userData.put("firstName", first_name);
                            userData.put("lastName", last_name);
                            userData.put("currentJobID", "none");

                            documentReference.set(userData);

                            Toast.makeText(RegisterActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error Creating Account!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btn_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}