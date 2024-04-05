package com.example.arithmeticforkids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.arithmeticforkids.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Setting click listeners for all buttons
        binding.additionButton.setOnClickListener(buttonClickLister);
        binding.subtractionButton.setOnClickListener(buttonClickLister);
        binding.multiplicationButton.setOnClickListener(buttonClickLister);
    }

    private final View.OnClickListener buttonClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == binding.additionButton) {
                Intent intent = Addition.additionFactory(getApplicationContext());
                startActivity(intent);
            } else if (v == binding.subtractionButton) {
                Intent intent = Subtraction.subtractionFactory(getApplicationContext());
                startActivity(intent);
            } else if (v == binding.multiplicationButton) {
                Intent intent = Multiplication.multiplicationFactory(getApplicationContext());
                startActivity(intent);
            }
        }
    };

}