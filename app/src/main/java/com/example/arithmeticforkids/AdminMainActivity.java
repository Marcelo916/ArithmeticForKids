package com.example.arithmeticforkids;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.entities.User;
import com.example.arithmeticforkids.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "package com.example.arithmeticforkids.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.arithmeticforkids.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.arithmeticforkids.SAVED_INSTANCE_STATE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.arithmeticforkids.SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;
    ActivityAdminMainBinding binding;
    private AdditionLogRepository repository;
    private int loggedInUserId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AdditionLogRepository.getRepository(getApplication());
        loginUserAdmin(savedInstanceState);

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        binding.AdminAdditionButton.setOnClickListener(buttonClickLister);
        binding.AdminSubtractionButton.setOnClickListener(buttonClickLister);
        binding.AdminMultiplicationButton.setOnClickListener(buttonClickLister);
        binding.AdminDivisionButton.setOnClickListener(buttonClickLister);
        binding.AdminToolsButton.setOnClickListener(buttonClickLister);

    }

    public void loginUserAdmin(Bundle savedInstanceState) {
        //check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(SHARED_PREFERENCE_USERID_VALUE)) {
            loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
        sharedPrefEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if (user == null) {
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminMainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        sharedPrefEditor.apply();

        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }


    static Intent adminActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminMainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private final View.OnClickListener buttonClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == binding.AdminAdditionButton) {
                Intent intent = Addition.additionFactory(getApplicationContext(), user.getId());
                intent.putExtra("operation", "addition");
                startActivity(intent);
            } else if (v == binding.AdminSubtractionButton) {
                Intent intent = Subtraction.subtractionFactory(getApplicationContext(), user.getId());
                intent.putExtra("operation", "subtraction");
                startActivity(intent);
            } else if (v == binding.AdminMultiplicationButton) {
                Intent intent = Multiplication.multiplicationFactory(getApplicationContext(), user.getId());
                intent.putExtra("operation", "multiplication");
                startActivity(intent);
            } else if (v == binding.AdminDivisionButton) {
                Intent intent = Division.divisionFactory(getApplicationContext(), user.getId());
                intent.putExtra("operation", "division");
                startActivity(intent);
            } else if (v == binding.AdminToolsButton) {
                Intent intent = AdminTools.adminToolsFactory(getApplicationContext(), user.getId());
                intent.putExtra("operation", "admin-tools");
                startActivity(intent);
            }
        }
    };
}