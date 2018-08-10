package com.gamora.gamoraapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gamora.gamoraapp.R;

public class LogInActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button signUpBtn = (Button) findViewById(R.id.signup_btn);
        clickSignUp(signUpBtn);
    }

    private void clickSignUp(Button signUpBtn)
    {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LogInActivity.this,SignUpActivity.class));
            }
        });
    }
}
