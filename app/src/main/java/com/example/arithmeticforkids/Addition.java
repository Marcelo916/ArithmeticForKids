package com.example.arithmeticforkids;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.arithmeticforkids.databinding.ActivityAdditionBinding;


public class Addition extends AppCompatActivity {
    ActivityAdditionBinding binding;

    Button goButton, answerA, answerB, answerC, answerD;
    TextView left, right, middle, bottom;
    ProgressBar timer;

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
            goButton.setVisibility(View.VISIBLE);

        }
    };


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Addition.this, "Clicking", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Addition.this, MainActivity.class);
                startActivity(intent);
                finish();
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

}