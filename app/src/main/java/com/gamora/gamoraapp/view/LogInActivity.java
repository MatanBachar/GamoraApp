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
import com.google.firebase.auth.FirebaseUser;

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
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
                finish();
            }
        });
    }

    private void logInHandler(Button logInBtn)
    {
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText emailText = (EditText) findViewById(R.id.email_text);
                EditText passwordText = (EditText) findViewById(R.id.password_text);
                String emailInput = emailText.getText().toString();
                String passInput = passwordText.getText().toString();
                if(InputCheck.isValidMail(emailInput)) {
                    mAuth.signInWithEmailAndPassword(emailInput, passInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogInActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
                }
                else {
                    emailText.setError(getString(R.string.error_invalid_email));
                }
            }
        });
    }
}
