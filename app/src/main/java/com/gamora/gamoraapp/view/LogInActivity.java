package com.gamora.gamoraapp.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gamora.gamoraapp.R;
import com.gamora.gamoraapp.view.Utils.InputCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button signUpBtn = (Button) findViewById(R.id.signup_btn);
        Button logInBtn = (Button) findViewById(R.id.login_btn);
        signUpHandler(signUpBtn);
        logInHandler(logInBtn);
    }

    private void signUpHandler(Button signUpBtn)
    {
        signUpBtn.setOnClickListener(v -> {
            startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
            finish();
        });
    }

    private void logInHandler(Button logInBtn)
    {
        logInBtn.setOnClickListener(v -> {
            EditText emailText = (EditText) findViewById(R.id.email_text);
            EditText passwordText = (EditText) findViewById(R.id.password_text);
            String emailInput = emailText.getText().toString();
            String passInput = passwordText.getText().toString();
            if(InputCheck.isValidMail(emailInput)) {
                mAuth.signInWithEmailAndPassword(emailInput, passInput).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(LogInActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, BaseMainActivity.class));
                        finish();
                    }
                });
            }
            else {
                emailText.setError(getString(R.string.error_invalid_email));
            }
        });
    }
}
