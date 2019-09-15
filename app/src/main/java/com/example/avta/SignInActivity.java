package com.example.avta;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.content.Intent;

public class SignInActivity extends AppCompatActivity {
    private String userId;
    private EditText usernameInput;
    private EditText passwordInput;


    public String generateUserId (String username, String password) {
        String userID = password + username;
        return userID;
    }



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }



}
