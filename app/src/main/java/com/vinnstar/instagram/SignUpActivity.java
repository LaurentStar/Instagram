package com.vinnstar.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private EditText etNewUser;
    private EditText etNewPassword;
    private EditText etEmail;
    private Button btnCreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNewUser = findViewById(R.id.etNewUser);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        etEmail = findViewById(R.id.etEmail);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etNewUser.getText().toString();
                String password = etNewPassword.getText().toString();
                String email = etEmail.getText().toString();
                createNewAccount(username, password, email);
            }
        });

    }

    private void createNewAccount(String username, String password, String email) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    goMainActivity();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG ,"Failed to login", e);
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
        finish();
    }
}

