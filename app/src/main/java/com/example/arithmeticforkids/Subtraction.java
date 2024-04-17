package com.example.arithmeticforkids;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.arithmeticforkids.database.AdditionLogRepository;
import com.example.arithmeticforkids.database.entities.SubtractionLog;
import com.example.arithmeticforkids.databinding.ActivitySubtractionBinding;

import java.util.ArrayList;

public class Subtraction extends AppCompatActivity {
    ActivitySubtractionBinding binding;
    private AdditionLogRepository repository;

    Button goButton, answerA, answerB, answerC, answerD;
    TextView left, right, middle, bottom;

    ProgressBar timer;
    int loggedInUserId = -1;

    Game game = new Game("subtraction");
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
            insertSubtractionRecord();
            updateDisplaySubtraction();
            goButton.setVisibility(View.VISIBLE);

        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubtractionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AdditionLogRepository.getRepository(getApplication());

        goButton = findViewById(R.id.startActionSubtraction);
        answerA = findViewById(R.id.answerASubtraction);
        answerB = findViewById(R.id.answerBSubtraction);
        answerC = findViewById(R.id.answerCSubtraction);
        answerD = findViewById(R.id.answerDSubtraction);

        answerA.setEnabled(false);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);

        timer = findViewById(R.id.progressBarSubtraction);

        left = findViewById(R.id.textViewLeftSubtraction);
        right = findViewById(R.id.textViewRightSubtraction);
        middle = findViewById(R.id.textViewMiddleSubtraction);
        bottom = findViewById(R.id.notificationDisplaySubtraction);

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
                game = new Game("subtraction");
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

        binding.goBackButtonSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Subtraction.this, MainActivity.class);
                //startActivity(intent);
                //finish();
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                startActivity(intent);
                finish();
            }
        });
        /**super.onCreate(savedInstanceState); This was the previous code. I am keeping it just in case I need to look at it
        binding = ActivitySubtractionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBackButtonSubtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subtraction.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

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

    public static Intent subtractionFactory(Context context) {
        Intent intent = new Intent(context, Subtraction.class);
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
                showLogoutDialogSubtraction();
                return false;
            }
        });

        return true;
    }

    private void showLogoutDialogSubtraction() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Subtraction.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutSubtraction();
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

    private void logoutSubtraction() {
        //TODO: Finish logout method
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }


    private void insertSubtractionRecord() {
        SubtractionLog log = new SubtractionLog(game.getScore(), loggedInUserId);
        repository.insertSubtractionLog(log);

    }

    public void updateDisplaySubtraction() {
        ArrayList<SubtractionLog> allLogsSubtraction = repository.getAllLogsSubtraction();

        StringBuilder sb = new StringBuilder();
        for(SubtractionLog log : allLogsSubtraction) {
            sb.append("You got ").append(game.getCorrect()).append(" out of ").append(game.getTotalQuestions() - 1).append(" questions!\n");
            sb.append(log);
        }
        bottom.setText(sb.toString());
    }

}