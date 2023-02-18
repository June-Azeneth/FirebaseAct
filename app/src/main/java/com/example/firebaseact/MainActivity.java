package com.example.firebaseact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String TAG = "FIRESTORE";
    FirebaseFirestore db;

    Button login;
    TextView toSignUp;
    Intent navToSignUp;
    TextInputEditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginbtn);
        toSignUp = findViewById(R.id.navToSignUp);
        email = findViewById(R.id.txtEditUsername_LogIn);
        password = findViewById(R.id.txtEditPass_login);


        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToSignUp = new Intent(MainActivity.this,Sign_Up.class);
                startActivity(navToSignUp);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();

                if(!emailInput.isEmpty()){
                    //searchUser();
                }else
                {
                    Toast.makeText(MainActivity.this,"Please fill in all the required details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void searchUser(String uname)
    {

        db.collection("EldriodActivity")
                .document(uname)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String queriedPassword = documentSnapshot.getString("password");
                        Log.d("Firestore", "Data read from Firestore: " + documentSnapshot.getData());

                        if(queriedPassword == null)
                        {
                            Toast.makeText(MainActivity.this,"User does not exist", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //passwordActionsEditText.setText(queriedPassword);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firestore", "Error reading data from Firestore: " + e.getMessage());
                    }
                });
    }
}