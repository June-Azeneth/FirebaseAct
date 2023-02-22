package com.example.firebaseact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Sign_Up extends AppCompatActivity {

    final String TAG = "FIRESTORE";
    FirebaseFirestore db;

    private FirebaseAuth mAuthSU;

    Button signUp;
    TextView toLogIn;
    Intent navToLogIn;
    TextInputEditText username, email, phone, address, password, confpass;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = FirebaseFirestore.getInstance();

        signUp = findViewById(R.id.sign_up);

        toLogIn = findViewById(R.id.toLogIn);

        username = findViewById(R.id.txtEditUsername);
        email = findViewById(R.id.txtEditEmail);
        phone = findViewById(R.id.txtEditPhone);
        address = findViewById(R.id.txtEditAddress);
        password = findViewById(R.id.txtEditPass);
        confpass = findViewById(R.id.txtEditConfPass);
        progressBar = findViewById(R.id.progressBar);
        mAuthSU = FirebaseAuth.getInstance();

        //NAV TO LOGIN PAGE
        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToLogIn = new Intent(Sign_Up.this, MainActivity.class);
                startActivity(navToLogIn);
            }
        });

        //USER SIGN UP
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = username.getText().toString();
                String emailInput = email.getText().toString();
                String phoneInput = phone.getText().toString();
                String addressInput = address.getText().toString();
                String passwordInput = password.getText().toString();
                String confPassInput = confpass.getText().toString();

                if (!usernameInput.isEmpty() || !emailInput.isEmpty() || !phoneInput.isEmpty() || !addressInput.isEmpty() || !passwordInput.isEmpty() || !confPassInput.isEmpty()) {
                    createAccount(usernameInput, emailInput, phoneInput, addressInput, passwordInput);
                } else {
                    Toast.makeText(Sign_Up.this, "Please fill in all the required details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createAccount(String username, String email, String phone, String address, String password) {

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("password", password);
        user.put("email", email);
        user.put("phone", phone);
        user.put("address", address);

        // Add a new document with a generated ID
        db.collection("EldroidActivity").document(username)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + username);
                        Toast.makeText(Sign_Up.this, "Successfully Added " + username, Toast.LENGTH_SHORT).show();
                        //progressBar.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Sign_Up.this, "Error adding user " + e, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error creating account", e);
                        //progressBar.setVisibility(View.GONE);
                    }
                });
    }
}