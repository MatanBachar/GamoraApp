package com.gamora.gamoraapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.model.data.PlatformManager;
import com.gamora.gamoraapp.model.data.User;

import java.util.HashMap;
import java.util.HashSet;



public class ConsolesActivity extends AppCompatActivity {

    private static final int NUM_OF_CONSOLES = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consoles);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button finishBtn = (Button) findViewById(R.id.finish_btn);
        Bundle registrationData = getIntent().getExtras();
    }

    public void finishHandler(Button finishBtn, final Bundle registraionData) {
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                HashSet<Integer> userPlatforms = new HashSet<>();
                for (int i = 1; i <= NUM_OF_CONSOLES; i++) {
                    int id = getResources().getIdentifier("check_"+i, "id", getPackageName());
                    CheckBox platform = (CheckBox) findViewById(id);
                    userPlatforms.add(i);
                }

                String userEmail = registraionData.getString("EMAIL");
                String userPass = registraionData.getString("PASSWORD");
                String userNickname = registraionData.getString("NICKNAME");
                String userRealname = registraionData.getString("REALNAME");

                User newUser = new User("0", userEmail,userPass,userNickname,userRealname, userPlatforms);
                startActivity(new Intent(ConsolesActivity.this, MainActivity.class));
            }
        });
    }
}
