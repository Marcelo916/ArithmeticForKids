package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.arithmeticforkids.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "package com.example.arithmeticforkids.MAIN_ACTIVITY_USER_ID";
    ActivityAdminMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.AdminAdditionButton.setOnClickListener(buttonClickLister);
        binding.AdminSubtractionButton.setOnClickListener(buttonClickLister);
        binding.AdminMultiplicationButton.setOnClickListener(buttonClickLister);
        binding.AdminDivisionButton.setOnClickListener(buttonClickLister);

    }


    static Intent adminActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminMainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private final View.OnClickListener buttonClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == binding.AdminAdditionButton) {
                Intent intent = Addition.additionFactory(getApplicationContext());
                intent.putExtra("operation", "addition");
                startActivity(intent);
            } else if (v == binding.AdminSubtractionButton) {
                Intent intent = Subtraction.subtractionFactory(getApplicationContext());
                intent.putExtra("operation", "subtraction");
                startActivity(intent);
            } else if (v == binding.AdminMultiplicationButton) {
                Intent intent = Multiplication.multiplicationFactory(getApplicationContext());
                intent.putExtra("operation", "multiplication");
                startActivity(intent);
            } else if (v == binding.AdminDivisionButton) {
                Intent intent = Division.divisionFactory(getApplicationContext());
                intent.putExtra("operation", "division");
                startActivity(intent);
            }
        }
    };
}