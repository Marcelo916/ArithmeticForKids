package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.arithmeticforkids.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.arithmeticforkids.MAIN_ACTIVITY_USER_ID";
    ActivityMainBinding binding;
    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();

        if(loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        //Setting click listeners for all buttons
        binding.additionButton.setOnClickListener(buttonClickLister);
        binding.subtractionButton.setOnClickListener(buttonClickLister);
        binding.multiplicationButton.setOnClickListener(buttonClickLister);
        binding.divisionButton.setOnClickListener(buttonClickLister);
    }

    private void loginUser() {
        //TODO: create login method
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;

    }

    private final View.OnClickListener buttonClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == binding.additionButton) {
                Intent intent = Addition.additionFactory(getApplicationContext());
                intent.putExtra("operation", "addition");
                startActivity(intent);
            } else if (v == binding.subtractionButton) {
                Intent intent = Subtraction.subtractionFactory(getApplicationContext());
                intent.putExtra("operation", "subtraction");
                startActivity(intent);
            } else if (v == binding.multiplicationButton) {
                Intent intent = Multiplication.multiplicationFactory(getApplicationContext());
                intent.putExtra("operation", "multiplication");
                startActivity(intent);
            } else if (v == binding.divisionButton) {
                Intent intent = Division.divisionFactory(getApplicationContext());
                intent.putExtra("operation", "division");
                startActivity(intent);
            }
        }
    };

}