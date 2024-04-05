package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.arithmeticforkids.databinding.ActivityAdditionBinding;


public class Addition extends AppCompatActivity {

    ActivityAdditionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addition.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public static Intent additionFactory(Context context) {
        Intent intent = new Intent(context, Addition.class);
        return intent;
    }


}