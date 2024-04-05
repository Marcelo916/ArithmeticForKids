package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arithmeticforkids.databinding.ActivitySubtractionBinding;

public class Subtraction extends AppCompatActivity {
    ActivitySubtractionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubtractionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBackButtonSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subtraction.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public static Intent subtractionFactory(Context context) {
        Intent intent = new Intent(context, Subtraction.class);
        return intent;
    }
}