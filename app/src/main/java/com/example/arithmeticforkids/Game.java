package com.example.arithmeticforkids;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private List<Question> questions;
    private int correct;
    private int incorrect;
    private int totalQuestions;
    private int score;
    private Question currentQuestion;
    private String operation;

    public Game(String operation) {
        this.operation = operation;
        correct = 0;
        incorrect = 0;
        totalQuestions = 0;
        score = 0;
        currentQuestion = new Question(10, operation);
        questions = new ArrayList<Question>();

    }

    public void newQuestion() {
        currentQuestion = new Question(totalQuestions * 2 + 5, operation);
        totalQuestions++;
        questions.add(currentQuestion);

    }

    public boolean checkAnswer(int submittedAnswer) {
        boolean isCorrect;
        if (currentQuestion.getAnswer() == submittedAnswer) {
            correct++;
            isCorrect = true;
        } else {
            incorrect++;
            isCorrect = false;
        }
        score = correct * 10 - incorrect * 10;
        return isCorrect;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return getCorrect() == game.getCorrect() && getIncorrect() == game.getIncorrect() && getTotalQuestions() == game.getTotalQuestions() && getScore() == game.getScore() && Objects.equals(getQuestions(), game.getQuestions()) && Objects.equals(getCurrentQuestion(), game.getCurrentQuestion()) && Objects.equals(operation, game.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestions(), getCorrect(), getIncorrect(), getTotalQuestions(), getScore(), getCurrentQuestion(), operation);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
