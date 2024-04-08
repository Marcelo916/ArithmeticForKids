package com.example.arithmeticforkids;

import java.util.Random;

public class Question {

    private int firstNumber;
    private int secondNumber;
    private int sum;
    private int[] storedNumbers;
    private int answerIndex;
    private int maxLimit;
    private String userPrompt;

    public Question(int maxLimit) {
        this.maxLimit = maxLimit;
        Random randomNumber = new Random();

        this.firstNumber = randomNumber.nextInt(maxLimit);
        this.secondNumber = randomNumber.nextInt(maxLimit);
        this.sum = this.firstNumber + this.secondNumber;
        this.userPrompt = firstNumber + " + " + secondNumber + " = ";

        this.answerIndex = randomNumber.nextInt(4);

        this.storedNumbers = new int[]{0, 1, 2, 3};

        this.storedNumbers[0] = sum + 1;
        this.storedNumbers[1] = sum + 7;
        this.storedNumbers[2] = sum - 2;
        this.storedNumbers[3] = sum - 5;

        this.storedNumbers = restoredNumbers(this.storedNumbers);

        storedNumbers[answerIndex] = sum;
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

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
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
