package com.example.arithmeticforkids;

import java.util.Random;

public class Question {

    private int firstNumber;
    private int secondNumber;
    private int answer;
    private int[] storedNumbers;
    private int answerIndex;
    private int maxLimit;
    private String userPrompt;

    public Question(int maxLimit, String operation) {
        this.maxLimit = maxLimit;
        Random randomNumber = new Random();

        if (operation.equals("subtraction")) {
            // Generate subtraction question
            this.firstNumber = randomNumber.nextInt(maxLimit);
            this.secondNumber = randomNumber.nextInt(maxLimit);
            this.answer = this.firstNumber - this.secondNumber;
            this.userPrompt = this.firstNumber + " - " + this.secondNumber + " = ";
        } else if (operation.equals("multiplication")) {
            // Generate multiplication question
            this.firstNumber = randomNumber.nextInt(10); // Limit first number for simplicity
            this.secondNumber = randomNumber.nextInt(10); // Limit second number for simplicity
            this.answer = this.firstNumber * this.secondNumber;
            this.userPrompt = this.firstNumber + " * " + this.secondNumber + " = ";
        } else {
            // Default to addition if operation is not specified or invalid
            this.firstNumber = randomNumber.nextInt(maxLimit);
            this.secondNumber = randomNumber.nextInt(maxLimit);
            this.answer = this.firstNumber + this.secondNumber;
            this.userPrompt = this.firstNumber + " + " + this.secondNumber + " = ";
        }

        this.answerIndex = randomNumber.nextInt(4);

        this.storedNumbers = new int[]{0, 1, 2, 3};

        this.storedNumbers[0] = answer + 1;
        this.storedNumbers[1] = answer + 7;
        this.storedNumbers[2] = answer - 2;
        this.storedNumbers[3] = answer - 5;

        this.storedNumbers = restoredNumbers(this.storedNumbers);

        storedNumbers[answerIndex] = answer;

        // Generate answer options for multiple-choice questions
        /**this.storedNumbers = new int[]{0, 1, 2, 3};
        this.answerIndex = randomNumber.nextInt(4); // Randomly select index for correct answer
        this.storedNumbers[answerIndex] = answer; // Set correct answer at correct index
        this.storedNumbers = restoredNumbers(this.storedNumbers);*/

        /**this.firstNumber = randomNumber.nextInt(maxLimit); This was the previous code. Keeping it just in case
        this.secondNumber = randomNumber.nextInt(maxLimit);
        this.answer = this.firstNumber + this.secondNumber;
        this.userPrompt = firstNumber + " + " + secondNumber + " = ";

        this.answerIndex = randomNumber.nextInt(4);

        this.storedNumbers = new int[]{0, 1, 2, 3};

        this.storedNumbers[0] = answer + 1;
        this.storedNumbers[1] = answer + 7;
        this.storedNumbers[2] = answer - 2;
        this.storedNumbers[3] = answer - 5;

        this.storedNumbers = restoredNumbers(this.storedNumbers);

        storedNumbers[answerIndex] = answer;*/
    }

    private int[] restoredNumbers(int[] array) {
        int index, temp;
        Random numberGenerator = new Random();

        for (int i = array.length - 1; i > 0; i--) {
            index = numberGenerator.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int[] getStoredNumbers() {
        return storedNumbers;
    }

    public void setStoredNumbers(int[] storedNumbers) {
        this.storedNumbers = storedNumbers;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getUserPrompt() {
        return userPrompt;
    }

    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }
}
