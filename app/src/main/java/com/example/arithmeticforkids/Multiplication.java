package com.example.arithmeticforkids;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.entities.MultiplicationLog;
import com.example.arithmeticforkids.database.entities.User;
import com.example.arithmeticforkids.databinding.ActivityMultiplicationBinding;

import java.util.ArrayList;

public class Multiplication extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "package com.example.arithmeticforkids.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.arithmeticforkids.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.arithmeticforkids.SAVED_INSTANCE_STATE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.arithmeticforkids.SHARED_PREFERENCE_USERID_VALUE";
    private static final int LOGGED_OUT = -1;

    ActivityMultiplicationBinding binding;
    private AdditionLogRepository repository;
    private int loggedInUserId = -1;
    private User user;

    Button goButton, answerA, answerB, answerC, answerD;
    TextView left, right, middle, bottom;

    ProgressBar timer;
    //int loggedInUserId = -1;

    Game game = new Game("multiplication");
    int secondsRemaining = 30;

    CountDownTimer clock = new CountDownTimer(30000, 1000) {
        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining--;
            left.setText(Integer.toString(secondsRemaining) + "sec");
            timer.setProgress(30 - secondsRemaining);

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onFinish() {
            answerA.setEnabled(false);
            answerB.setEnabled(false);
            answerC.setEnabled(false);
            answerD.setEnabled(false);
            bottom.setText("You got " + game.getCorrect() + " out of " + (game.getTotalQuestions() - 1) + " questions!");
            insertMultiplicationRecord();
            updateDisplayMultiplication();
            goButton.setVisibility(View.VISIBLE);

        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultiplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AdditionLogRepository.getRepository(getApplication());
        loginUserMultiplication(savedInstanceState);

        goButton = findViewById(R.id.startActionMultiplication);
        answerA = findViewById(R.id.answerAMultiplication);
        answerB = findViewById(R.id.answerBMultiplication);
        answerC = findViewById(R.id.answerCMultiplication);
        answerD = findViewById(R.id.answerDMultiplication);

        answerA.setEnabled(false);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);

        timer = findViewById(R.id.progressBarMultiplication);

        left = findViewById(R.id.textViewLeftMultiplication);
        right = findViewById(R.id.textViewRightMultiplication);
        middle = findViewById(R.id.textViewMiddleMultiplication);
        bottom = findViewById(R.id.notificationDisplayMultiplication);

        left.setText("0sec");
        middle.setText("");
        right.setText("0pts");
        bottom.setText("Press Go!");


        View.OnClickListener startButtonClickLister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button) v;

                start_button.setVisibility(View.INVISIBLE);

                secondsRemaining = 30;
                game = new Game("multiplication");
                nextTurn();
                clock.start();

            }
        };

        View.OnClickListener answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;

                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                game.checkAnswer(answerSelected);
                right.setText(Integer.toString(game.getScore()) + "pts");
                nextTurn();

            }
        };

        goButton.setOnClickListener(startButtonClickLister);

        answerA.setOnClickListener(answerClickListener);
        answerB.setOnClickListener(answerClickListener);
        answerC.setOnClickListener(answerClickListener);
        answerD.setOnClickListener(answerClickListener);

        binding.goBackButtonMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Multiplication.this, MainActivity.class);
                //startActivity(intent);
                //finish();
                /**Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                startActivity(intent);
                finish();*/
                if(user.isAdmin()) {
                    startActivity(AdminMainActivity.adminActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void nextTurn() {
        game.newQuestion();
        int [] answer = game.getCurrentQuestion().getStoredNumbers();

        answerA.setText(Integer.toString(answer[0]));
        answerB.setText(Integer.toString(answer[1]));
        answerC.setText(Integer.toString(answer[2]));
        answerD.setText(Integer.toString(answer[3]));

        answerA.setEnabled(true);
        answerB.setEnabled(true);
        answerC.setEnabled(true);
        answerD.setEnabled(true);

        middle.setText(game.getCurrentQuestion().getUserPrompt());
        bottom.setText(game.getCorrect() + "/" + (game.getTotalQuestions() - 1));
    }

    public void loginUserMultiplication(Bundle savedInstanceState) {
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, loggedInUserId);
        sharedPrefEditor.apply();
    }

    public static Intent multiplicationFactory(Context context, int userId) {
        Intent intent = new Intent(context, Multiplication.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null) {
            return false;
        }
        item.setTitle(user.getUsername());

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialogMultiplication();
                return false;
            }
        });

        return true;
    }

    private void showLogoutDialogMultiplication() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Multiplication.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutMultiplication();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();;
    }


    private void logoutMultiplication() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        sharedPrefEditor.apply();

        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void insertMultiplicationRecord(){
        MultiplicationLog log = new MultiplicationLog(game.getScore(), loggedInUserId);
        repository.insertMultiplicationLog(log);
    }

    public void updateDisplayMultiplication() {
        ArrayList<MultiplicationLog> allLogsMultiplication = repository.getAllLogsMultiplication();

        StringBuilder sb = new StringBuilder();
        for(MultiplicationLog log : allLogsMultiplication) {
            sb.append("You got ").append(game.getCorrect()).append(" out of ").append(game.getTotalQuestions() - 1).append(" questions!\n");
            sb.append(log);
        }
        bottom.setText(sb.toString());
    }


}