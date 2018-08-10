package com.gamora.gamoraapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.view.Utils.InputCheck;
import com.google.android.gms.common.SignInButton;

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
                EditText emailText = (EditText) findViewById(R.id.email_text);
                EditText passwordText = (EditText) findViewById(R.id.password_text);
                EditText nicknameText = (EditText) findViewById(R.id.nickname_text);
                EditText realnameText = (EditText) findViewById(R.id.realname_text);
                Intent registrationStep = new Intent(SignUpActivity.this, ConsolesActivity.class);
                String emailInput = emailText.getEditableText().toString();
                if(InputCheck.isValidMail(emailInput))
                {
                    registrationStep.putExtra("EMAIL", emailText.getEditableText().toString());
                    registrationStep.putExtra("PASSWORD", passwordText.getEditableText().toString());
                    registrationStep.putExtra("NICKNAME", nicknameText.getEditableText().toString());
                    registrationStep.putExtra("REALNAME", realnameText.getEditableText().toString());
                    startActivity(registrationStep);
                    finish();
                }
                else {
                    emailText.setError(getString(R.string.error_invalid_email));
                }
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
