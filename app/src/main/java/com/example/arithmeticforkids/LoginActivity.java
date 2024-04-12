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
                if(!verifyUser()) {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                    startActivity(intent);
                }
            }
        });
    }

    //We will have to update this method. The code is to test the login page working properly.
    private boolean verifyUser() {
        //Hardcoded valid user and password
        String validUsername = "example_user";
        String validPassword = "password123";

        // Get the entered username and password from Text fields
        String enteredUsername = binding.userName.getText().toString().trim();
        String enteredPassword = binding.password.getText().toString().trim();

        // Check if the entered username and password match the valid credentials
        return enteredUsername.equals(validUsername) && enteredPassword.equals(validPassword);

    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

}