package com.example.arithmeticforkids;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.entities.AdditionLog;
import com.example.arithmeticforkids.databinding.ActivityAdditionBinding;

import java.util.ArrayList;
import java.util.Locale;


public class Addition extends AppCompatActivity {
    ActivityAdditionBinding binding;
    private AdditionLogRepository repository;

    Button goButton, answerA, answerB, answerC, answerD;
    TextView left, right, middle, bottom;
    ProgressBar timer;
    //private boolean isAdmin;
    //private LoginActivity loginActivity = new LoginActivity();

    Game game = new Game("addition");
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
            insertAdditionRecord();
            updateDisplay();
            goButton.setVisibility(View.VISIBLE);

        }
    };


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AdditionLogRepository.getRepository(getApplication());

        goButton = findViewById(R.id.startAction);
        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);

        answerA.setEnabled(false);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);

        timer = findViewById(R.id.progressBarAddition);

        left = findViewById(R.id.textViewLeft);
        right = findViewById(R.id.textViewRight);
        middle = findViewById(R.id.textViewMiddle);
        bottom = findViewById(R.id.notificationDisplay);

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
                game = new Game("addition");
                //insertAdditionRecord();
                nextTurn();
                right.setText(Integer.toString(game.getScore()) + "pts");
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


        //int isAdmin = loginActivity.verifyUser();

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Addition.this, MainActivity.class);
                //startActivity(intent);
                //finish();
                //TODO: Here is the bug "You should check if the user is Admin or not" then go back to the respective factory
                //Intent intent = AdminMainActivity.adminActivityIntentFactory(getApplicationContext(), 0);
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                startActivity(intent);
                finish();

                /**Intent intent;
                if (isAdmin == -1) {
                    intent = new Intent(Addition.this, AdminMainActivity.class);
                } else {
                    intent = new Intent(Addition.this, MainActivity.class);
                }
                startActivity(intent);*/
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

    public static Intent additionFactory(Context context) {
        Intent intent = new Intent(context, Addition.class);
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
        item.setTitle("Marcelo");

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialogAddition();
                return false;
            }
        });

        return true;
    }

    private void showLogoutDialogAddition() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Addition.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutAddition();
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

    private void logoutAddition() {
        //TODO: Finish logout method
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    //This method inserts the records to the database
    private void insertAdditionRecord() {
        AdditionLog log = new AdditionLog(game.getScore());
        repository.insertAdditionLog(log);
    }


    //This method retrieves records from the database
    private void updateDisplay() {
        ArrayList<AdditionLog> allLogs = repository.getAllLogs();

        StringBuilder sb = new StringBuilder();
        for(AdditionLog log : allLogs) {
            sb.append("You got ").append(game.getCorrect()).append(" out of ").append(game.getTotalQuestions() - 1).append(" questions!\n");
            sb.append(log);
        }
        bottom.setText(sb.toString());
        //right.setText(Integer.toString(game.getScore()) + "pts");


        //String currentInfo = binding.textViewRight.getText().toString();
        //Log.d(MainActivity.TAG, "Current info: " + currentInfo);
        //String newDisplay = String.format(Locale.US, "")
        //Log.i(MainActivity.TAG, repository.getAllLogs().toString());
    }

}