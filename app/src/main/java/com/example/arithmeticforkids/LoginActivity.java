package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arithmeticforkids.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loggingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
                /**if(!verifyUser()) {
                 Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                 } else {
                 Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                 startActivity(intent);
                 }*/
            }
        });
    }

    //We will have to update this method. The code is to test the login page working properly.
    public int verifyUser() {
        //Hardcoded valid user and password
        String validUsername = "example_user";
        String validPassword = "password123";

        //Hardcoded valid user and password for admin
        String adminUsername = "admin_user";
        String adminPassword = "admin123";

        // Get the entered username and password from Text fields
        String enteredUsername = binding.userName.getText().toString().trim();
        String enteredPassword = binding.password.getText().toString().trim();

        if (enteredUsername.equals(validUsername) && enteredPassword.equals(validPassword)) {
            //startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("isAdmin", false));
            startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), 0));
            return 0;
        } else if (enteredUsername.equals(adminUsername) && enteredPassword.equals(adminPassword)) {
            //startActivity(new Intent(LoginActivity.this, AdminMainActivity.class).putExtra("isAdmin", true));
            startActivity(AdminMainActivity.adminActivityIntentFactory(getApplicationContext(), -1));
            return -1;
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            return -2;
        }

        // Check if the entered username and password match the valid credentials
        //return enteredUsername.equals(validUsername) && enteredPassword.equals(validPassword);

    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

}