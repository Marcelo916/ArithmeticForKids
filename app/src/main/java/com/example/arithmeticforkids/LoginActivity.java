package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.entities.User;
import com.example.arithmeticforkids.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AdditionLogRepository repository;
    //private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AdditionLogRepository.getRepository(getApplication());

        binding.loggingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUserOne();
                //verifyUser();
                /**if(!verifyUserOne()) {
                 toastMaker("Invalid username or password!");
                 } else {
                 Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                 startActivity(intent);
                 }*/
            }
        });
    }

    private void verifyUserOne() {
        String username = binding.userName.getText().toString();
        if(username.isEmpty()) {
            toastMaker("Username should not be blank!");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null) {
                String password = binding.password.getText().toString();
                if(password.equals(user.getPassword())) {
                    //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
                    //SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                    //sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, user.getId());
                    //sharedPrefEditor.apply();
                    if(user.isAdmin()) {
                        startActivity(AdminMainActivity.adminActivityIntentFactory(getApplicationContext(), user.getId()));
                    } else {
                        startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                    }
                    //startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    toastMaker("Invalid password!");
                    binding.password.setSelection(0);
                }
            } else {
                toastMaker(String.format("No user %s is not a valid username.", username));
                binding.password.setSelection(0);
            }
        });
        /**user = repository.getUserByUserName(username);
        if(user != null) {
            String password = binding.password.getText().toString();
            if(password.equals(user.getPassword())) {
                return true;
            } else {
                toastMaker("Invalid password!");
                return false;
            }
        }
        toastMaker(String.format("No %s found", username));
        return false;*/
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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