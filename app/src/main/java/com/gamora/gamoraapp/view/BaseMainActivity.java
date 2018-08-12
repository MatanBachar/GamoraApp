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
import com.gamora.gamoraapp.model.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BaseMainActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mRootRef;

    //Current user data from database
    private User userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.action_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.action_add_post:
                    selectedFragment = new AddPostFragment();
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
        });
        // startActivity(new Intent(BaseMainActivity.this, LogInActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        try {
            if (currentUser == null) {
                // User is not signed in
                Intent notConnected = new Intent(BaseMainActivity.this, LogInActivity.class);
                startActivity(notConnected);
                finish();
            }
            Query query = mRootRef.getReference("users").orderByChild("uid").equalTo(currentUser.getUid());
            query.addListenerForSingleValueEvent(valueEventListener);
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        } catch (Exception e) {
            Toast.makeText(BaseMainActivity.this, "Problem loading user", Toast.LENGTH_LONG).show();
        }
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            System.out.println(dataSnapshot.toString());
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    userData = snapshot.getValue(User.class);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(BaseMainActivity.this, databaseError.toString(), Toast.LENGTH_LONG).show();
        }
    };

    public User getUserData() {
        return userData;
    }

}
