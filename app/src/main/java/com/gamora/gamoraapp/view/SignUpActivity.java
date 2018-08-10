package com.gamora.gamoraapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gamora.gamoraapp.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Button nextBtn = (Button) findViewById(R.id.next_btn);
        Button cancelBtn = (Button) findViewById(R.id.cancel_btn);
        nextHandler(nextBtn);
        cancelHandler(cancelBtn);
    }


    public void nextHandler(Button nextBtn) {
        //Continue to the next registration step
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = (EditText) findViewById(R.id.email);
                EditText passwordText = (EditText) findViewById(R.id.password);
                EditText nicknameText = (EditText) findViewById(R.id.nickname);
                EditText realnameText = (EditText) findViewById(R.id.realname_text);
                Intent registrationStep = new Intent(SignUpActivity.this, ConsolesActivity.class);
                registrationStep.putExtra("EMAIL", emailText.getText());
                registrationStep.putExtra("PASSWORD", passwordText.getText());
                registrationStep.putExtra("NICKNAME", nicknameText.getText());
                registrationStep.putExtra("REALNAME", realnameText.getText());
                startActivity(new Intent(SignUpActivity.this, ConsolesActivity.class));
            }
        });
    }

    public void cancelHandler(Button cancelBtn) {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });
    }
}
