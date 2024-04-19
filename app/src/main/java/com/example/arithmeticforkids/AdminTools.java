package com.example.arithmeticforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.entities.User;
import com.example.arithmeticforkids.databinding.ActivityAdminToolsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    List<String> userList = new ArrayList<>();

    //todo: finish UserListDisplay so it outputs the contents of repository.GetAllUsers() and updates during onCreate() or when a user is deleted
    //todo: hook up spinner to list provided by getAllUsers(). https://developer.android.com/develop/ui/views/components/spinner
    //todo: make a button to delete a user that is selected in the spinner

    //todo: implement the settings panel so the user can log out in this activity?



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

        binding.ShowUsersButtonAdminTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshDisplay();
            }
        });

        binding.goBackButtonAdminTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AdminMainActivity.adminActivityIntentFactory(getApplicationContext(), user.getId()));
            }
        });
    }

    private void refreshDisplay(){
        TextView UserListDisplay = findViewById(R.id.UserListDisplayWindow);
//        LiveData<List<User>> UserLogList = repository.getAllUsers();
//        List<String> userList = new ArrayList<>();
//        LiveData<List<String>> UserLogListString = Transformations.map(repository.getAllUsers(),
        repository.getAllUsers().observe(this, userList->{ //I'm seriously at my wits end here. I got the list but IT'S STILL OBJECT IDs
            StringBuilder stringBuilder = new StringBuilder(); //I can't figure out how to convert to LiveData<List<String>> and observe it
            for (User user : userList) {
                stringBuilder.append(user.toString()).append("\n");
            }
            String usersString = stringBuilder.toString();
//            this.userList.addAll(userList);
        /**List<String> usersList = new ArrayList<>(); //temporary solution because i can't figure out how to call the actual usernames from the database
        usersList.add("admin1");
        usersList.add("testuser1");**/
        if(!usersString.isEmpty()){
            UserListDisplay.setText(usersString); //todo: figure out how to return list data instead of object name: "admin1, exampleuser1"
        }else{
            UserListDisplay.setText(R.string.no_users_message);
        }
        });
    }
//    private void refreshDisplay(){
//        TextView UserListDisplay = findViewById(R.id.UserListDisplayWindow);
//        LiveData<List<User>> UserLogList = repository.getAllUsers();
//        if(!UserLogList.toString().isEmpty()){
//            UserListDisplay.setText(UserLogList.toString()); //figure out how to return list data instead of object name
//        }else{
//            UserListDisplay.setText(R.string.no_users_message);
//        }
//    }

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