package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arithmeticforkids.databinding.ActivityMultiplicationBinding;

public class Multiplication extends AppCompatActivity {

    ActivityMultiplicationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultiplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBackButtonMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Multiplication.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public static Intent multiplicationFactory(Context context) {
        Intent intent = new Intent(context, Multiplication.class);
        return intent;
    }


}