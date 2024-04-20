package com.example.arithmeticforkids;

import static androidx.core.graphics.TypefaceCompat.clearCache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

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

//    List<String> userList = new ArrayList<>();

    //todo: implement the settings panel so the user can log out in this activity?



    public static Intent adminToolsFactory(Context context, int userId) {
        Intent intent = new Intent(context, AdminTools.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.arithmeticforkids.databinding.ActivityAdminToolsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AdditionLogRepository.getRepository(getApplication());
        loginUserAdminTools(savedInstanceState);

        binding.ShowUsersButtonAdminTools.setOnClickListener(v -> refreshDisplay());

        binding.AddUserButtonAdminTools.setOnClickListener(v -> addUser());

        binding.AddAdminButtonAdminTools.setOnClickListener(v -> addAdmin());

        binding.ClearCacheButtonAdminTools.setOnClickListener(v -> clearCache());

        binding.goBackButtonAdminTools.setOnClickListener(v -> startActivity(AdminMainActivity.adminActivityIntentFactory(getApplicationContext(), user.getId())));

        TextView textView = findViewById(R.id.UserListDisplayWindow);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void addUser(){
        User newUser = new User("newUser", "newUser");
        repository.insertUser(newUser);
    }

    private void addAdmin(){
        User newAdmin = new User("newAdmin", "newAdmin");
        newAdmin.setAdmin(true);
        repository.insertUser(newAdmin);
    }

    private void refreshDisplay(){
        TextView UserListDisplay = findViewById(R.id.UserListDisplayWindow);
//        LiveData<List<User>> UserLogList = repository.getAllUsers();
//        List<String> userList = new ArrayList<>();
//        LiveData<List<String>> UserLogListString = Transformations.map(repository.getAllUsers(),
        repository.getAllUsers().observe(this, userList->{
            StringBuilder stringBuilder = new StringBuilder();
            for (User user : userList) {
                stringBuilder.append("User: ").append(user.getUsername()).append(" ID: ").append(user.getId()).append("\n");
                if(!repository.getAdditionRecordForUser(user.getId()).isEmpty()) {
                    stringBuilder.append("Addition\n").append(repository.getAdditionRecordForUser(user.getId())).append("\n");
                }else{
                    stringBuilder.append("Addition\n").append("No Scores found.\n");
                }
                if(!repository.getSubtractionRecordForUser(user.getId()).isEmpty()) {
                    stringBuilder.append("Subtraction\n").append(repository.getSubtractionRecordForUser(user.getId())).append("\n");
                }else{
                    stringBuilder.append("Subtraction\n").append("No Scores found.\n");
                }
                if(!repository.getMultiplicationRecordForUser(user.getId()).isEmpty()) {
                    stringBuilder.append("Multiplication\n").append(repository.getMultiplicationRecordForUser(user.getId())).append("\n");
                }else{
                    stringBuilder.append("Multiplication\n").append("No Scores found.\n");
                }
                if(!repository.getDivisionRecordForUser(user.getId()).isEmpty()) {
                    stringBuilder.append("Division\n").append(repository.getDivisionRecordForUser(user.getId())).append("\n\n");
                }else{
                    stringBuilder.append("Division\n").append("No Scores found.\n\n");
                }
            }
            String usersString = stringBuilder.toString();
//            this.userList.addAll(userList);
//        List<String> usersList = new ArrayList<>(); //temporary solution because i can't figure out how to call the actual usernames from the database
//        usersList.add("admin1");
//        usersList.add("testuser1");
        if(!usersString.isEmpty()){
            UserListDisplay.setText(usersString);
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