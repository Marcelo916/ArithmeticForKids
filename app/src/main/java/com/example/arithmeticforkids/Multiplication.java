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

import androidx.appcompat.app.AppCompatActivity;

import com.example.arithmeticforkids.databinding.ActivityMultiplicationBinding;

public class Multiplication extends AppCompatActivity {

    ActivityMultiplicationBinding binding;

    Button goButton, answerA, answerB, answerC, answerD;
    TextView left, right, middle, bottom;

    ProgressBar timer;

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
            goButton.setVisibility(View.VISIBLE);

        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultiplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
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

    public static Intent multiplicationFactory(Context context) {
        Intent intent = new Intent(context, Multiplication.class);
        return intent;
    }


}