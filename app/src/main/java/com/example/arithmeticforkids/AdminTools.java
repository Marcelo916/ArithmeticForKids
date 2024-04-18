package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.entities.User;
import com.example.arithmeticforkids.databinding.ActivityAdminToolsBinding;

public class AdminTools extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "package com.example.arithmeticforkids.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.arithmeticforkids.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.arithmeticforkids.SAVED_INSTANCE_STATE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.arithmeticforkids.SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;

    ActivityAdminToolsBinding binding;
    private AdditionLogRepository repository;
    private int loggedInUserId = -1;
    private User user;

    Button displayUsersButton;

    public static Intent adminToolsFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminTools.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.arithmeticforkids.databinding.ActivityAdminToolsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AdditionLogRepository.getRepository(getApplication());
        loginUserAdminTools(savedInstanceState);

        displayUsersButton = findViewById(R.id.AdminUsersButton); //button to display list of all users from database

        binding.goBackButtonAdminTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminMainActivity.adminActivityIntentFactory(getApplicationContext(), user.getId()));
            }
        });
    }

    public void loginUserAdminTools(Bundle savedInstanceState) {
        //check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(SHARED_PREFERENCE_USERID_VALUE)) {
            loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user ->{
            this.user = user;
            if(this.user != null) {
                invalidateOptionsMenu();
            } else {
                //  logout();
            }
        });
    }

}