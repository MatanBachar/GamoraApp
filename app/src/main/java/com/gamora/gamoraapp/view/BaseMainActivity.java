package com.gamora.gamoraapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gamora.gamoraapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseMainActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.action_add_post:
                        selectedFragment = new AddPostFragment();
                        break;
                    case R.id.action_messages:
                        selectedFragment = new MessagesFragment();
                        break;
                    case R.id.action_hot:
                        selectedFragment = new HotFragment();
                        break;
                    case R.id.action_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                    default:
                        selectedFragment = new HomeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , selectedFragment).commit();
                return true;
            }
        });
        // startActivity(new Intent(BaseMainActivity.this, LogInActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not signed in
            Intent notConnected = new Intent(BaseMainActivity.this, LogInActivity.class);
            startActivity(notConnected);
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
    }

}
