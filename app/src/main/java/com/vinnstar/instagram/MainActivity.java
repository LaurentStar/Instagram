package com.vinnstar.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;
import com.vinnstar.instagram.fragments.ComposeFragment;
import com.vinnstar.instagram.fragments.PostsFragment;
import com.vinnstar.instagram.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private Button btnLogout;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        btnLogout = findViewById(R.id.btnLogout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                goLoginActivity();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           Fragment fragment;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostsFragment();
                        Toast.makeText(MainActivity.this, "What up Homie?", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        Toast.makeText(MainActivity.this, "compose?", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();

                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        Toast.makeText(this, "GET OUT OF MY SITE!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
