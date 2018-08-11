package com.gamora.gamoraapp.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.model.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ConsolesActivity extends AppCompatActivity {

    private static final int NUM_OF_CONSOLES = 4;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabaseRef;
    Bundle registrationData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consoles);
        mAuth = FirebaseAuth.getInstance();
        usersDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button finishBtn = (Button) findViewById(R.id.finish_btn);
        Bundle registrationData = getIntent().getExtras();
        finishHandler(finishBtn, registrationData);
    }

    public void finishHandler(Button finishBtn, final Bundle registrationData) {
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<Integer> userPlatforms = new ArrayList<>();
                for (int i = 1; i <= NUM_OF_CONSOLES; i++) {
                    int id = getResources().getIdentifier("check_" + i, "id", getPackageName());
                    CheckBox platform = (CheckBox) findViewById(id);
                    if (platform.isChecked())
                        userPlatforms.add(i);
                }

                final String userEmail = registrationData.getString("EMAIL");
                final String userPass = registrationData.getString("PASSWORD");
                final String userNickname = registrationData.getString("NICKNAME");
                final String userRealname = registrationData.getString("REALNAME");

                //Creating the user for firebase's user ID and later authentication
                mAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String firebaseUserID = mAuth.getUid();
                            User newUser = new User(firebaseUserID, userEmail,userPass,userNickname,userRealname, userPlatforms);
                            Toast.makeText(ConsolesActivity.this, "Signing you up...", Toast.LENGTH_LONG).show();
                            //Adding the user into the Firebase Realtime-database
                            usersDatabaseRef.child(firebaseUserID).setValue(newUser);
                            startActivity(new Intent(ConsolesActivity.this, BaseMainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ConsolesActivity.this, "Problem creating user, try again later", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ConsolesActivity.this, LogInActivity.class));
                            finish();
                        }
                    }
                });


            }
        });
    }
}

