package com.example.avta;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;


public class SignInActivity extends AppCompatActivity {
    private String userId;
    private EditText usernameInput, passwordInput;


    public String generateUserId (String username, String password) {
        String userID = password + username;
        return userID;
    }




    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("Login");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_set_event, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complete_button) {
            // Hide keyboard
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            String username = ((EditText) findViewById(R.id.usernameInput)).getText().toString().trim();
            // Check that event name is not empty
            if (username.length() == 0) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Username can't be empty", Snackbar.LENGTH_LONG);
                snackbar.show();
                return super.onOptionsItemSelected(item);
            }
            String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString().trim();
            // Check that event name is not empty
            if (password.length() == 0) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Password can't be empty", Snackbar.LENGTH_LONG);
                snackbar.show();
                return super.onOptionsItemSelected(item);
            }
            userId = generateUserId(username, password);
            Intent intent = new Intent();
            intent.putExtra("userId", userId);
            setResult(RESULT_OK, intent);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
