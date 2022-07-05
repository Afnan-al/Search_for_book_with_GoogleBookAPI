package com.gtappdevelopers.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    Button buttonSignup;

    //Creating method to move to next activity
    public void showBookList() {
        Intent intent = new Intent(MainActivity.this, BookList.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //initializing login page
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);

        //initializing on click listner for our buttons.
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextUsername.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty()) {
                    ParseUser.logInInBackground(editTextUsername.getText().toString(), editTextPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                showBookList();
                            } else {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextUsername.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty()) {
                    ParseUser user = new ParseUser();
                    user.setUsername(editTextUsername.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                                showBookList();
                            } else {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}