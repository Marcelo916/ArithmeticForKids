package com.example.arithmeticforkids;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuestionTest {

    private Question gameOne;
    private Question gameTwo;
    private Question gameThree;
    private Question gameFour;

    @Before
    public void setUp() throws Exception {
        gameOne = new Question(10, "addition");
        gameTwo = new Question(10, "subtraction");
        gameThree = new Question(10, "multiplication");
        gameFour = new Question(10, "division");
    }

    @After
    public void tearDown() throws Exception {
        gameOne = null;
        gameTwo = null;
        gameThree = null;
        gameFour = null;
    }

    @Test
    public void Question() {
        assertNotNull(gameOne);
        assertNotNull(gameTwo);
        assertNotNull(gameThree);
        assertNotNull(gameFour);

        assertTrue(gameOne.getFirstNumber() >= 0 && gameOne.getFirstNumber() <= 10);
        assertTrue(gameTwo.getFirstNumber() >= 0 && gameTwo.getFirstNumber() <= 10);
        assertTrue(gameThree.getFirstNumber() >= 0 && gameThree.getFirstNumber() <= 10);
        assertTrue(gameFour.getFirstNumber()* gameFour.getSecondNumber() >= 0);
        assertTrue(gameFour.getSecondNumber() >= 1);

        assertTrue(gameOne.getSecondNumber() >= 0 && gameOne.getSecondNumber() <= 10);
        assertTrue(gameTwo.getSecondNumber() >= 0 && gameTwo.getSecondNumber() <= 10);
        assertTrue(gameThree.getSecondNumber() >= 0 && gameThree.getSecondNumber() <= 10);
        assertTrue(gameFour.getSecondNumber() >= 1 && gameFour.getSecondNumber() <= 10);

        assertEquals(gameOne.getFirstNumber() + gameOne.getSecondNumber(), gameOne.getAnswer());
        assertEquals(gameTwo.getFirstNumber() - gameTwo.getSecondNumber(), gameTwo.getAnswer());
        assertEquals(gameThree.getFirstNumber() * gameThree.getSecondNumber(), gameThree.getAnswer());
        assertEquals(gameFour.getFirstNumber() / gameFour.getSecondNumber(), gameFour.getAnswer());
    }

    @Test
    public void getFirstNumber() {
        gameOne.setFirstNumber(1);
        gameTwo.setFirstNumber(4);
        gameThree.setFirstNumber(7);
        gameFour.setFirstNumber(9);

        assertEquals(1, gameOne.getFirstNumber());
        assertEquals(4, gameTwo.getFirstNumber());
        assertEquals(7, gameThree.getFirstNumber());
        assertEquals(9, gameFour.getFirstNumber());

        assertNotEquals(2, gameOne.getFirstNumber());
        assertNotEquals(1, gameTwo.getFirstNumber());
        assertNotEquals(3, gameThree.getFirstNumber());
        assertNotEquals(5, gameFour.getFirstNumber());
    }

    @Test
    public void setFirstNumber() {
        int expectedFirstNumber = 9;

        gameOne.setFirstNumber(expectedFirstNumber);
        gameTwo.setFirstNumber(expectedFirstNumber);
        gameThree.setFirstNumber(expectedFirstNumber);
        gameFour.setFirstNumber(expectedFirstNumber);

        assertEquals(expectedFirstNumber, gameOne.getFirstNumber());
        assertEquals(expectedFirstNumber, gameTwo.getFirstNumber());
        assertEquals(expectedFirstNumber, gameThree.getFirstNumber());
        assertEquals(expectedFirstNumber, gameThree.getFirstNumber());

        assertNotEquals(3, gameOne.getFirstNumber());
        assertNotEquals(5, gameOne.getFirstNumber());
        assertNotEquals(7, gameOne.getFirstNumber());
        assertNotEquals(10, gameOne.getFirstNumber());

        assertNotNull(gameOne);
        assertNotNull(gameTwo);
        assertNotNull(gameThree);
        assertNotNull(gameFour);
    }

    @Test
    public void getSecondNumber() {
        gameOne.setSecondNumber(1);
        gameTwo.setSecondNumber(3);
        gameThree.setSecondNumber(9);
        gameFour.setSecondNumber(10);

        assertEquals(1, gameOne.getSecondNumber());
        assertEquals(3, gameTwo.getSecondNumber());
        assertEquals(9, gameThree.getSecondNumber());
        assertEquals(10, gameFour.getSecondNumber());

        assertNotEquals(10, gameOne.getSecondNumber());
        assertNotEquals(5, gameTwo.getSecondNumber());
        assertNotEquals(6, gameThree.getSecondNumber());
        assertNotEquals(7, gameFour.getSecondNumber());
    }

    @Test
    public void setSecondNumber() {
        int expectedSecondNumber = 7;

        gameOne.setSecondNumber(expectedSecondNumber);
        gameTwo.setSecondNumber(expectedSecondNumber);
        gameThree.setSecondNumber(expectedSecondNumber);
        gameFour.setSecondNumber(expectedSecondNumber);

        assertEquals(expectedSecondNumber, gameOne.getSecondNumber());
        assertEquals(expectedSecondNumber, gameTwo.getSecondNumber());
        assertEquals(expectedSecondNumber, gameThree.getSecondNumber());
        assertEquals(expectedSecondNumber, gameFour.getSecondNumber());

        assertNotEquals(3, gameOne.getSecondNumber());
        assertNotEquals(8, gameTwo.getSecondNumber());
        assertNotEquals(9, gameThree.getSecondNumber());
        assertNotEquals(1, gameFour.getSecondNumber());

        assertNotNull(gameOne);
        assertNotNull(gameTwo);
        assertNotNull(gameThree);
        assertNotNull(gameFour);
    }

    @Test
    public void getAnswer() {
        int firstNumber = 5;
        int secondNumber = 3;
        int expectedAnswer = firstNumber + secondNumber;
        int unexpectedAnswer = firstNumber * secondNumber;

        gameOne.setFirstNumber(firstNumber);
        gameOne.setSecondNumber(secondNumber);
        gameOne.setAnswer(8);

        assertEquals(expectedAnswer, gameOne.getAnswer());
        assertNotEquals(unexpectedAnswer, gameOne.getAnswer());

        int expectedSubtraction = firstNumber - secondNumber;

        gameTwo.setFirstNumber(firstNumber);
        gameTwo.setSecondNumber(secondNumber);
        gameTwo.setAnswer(2);

        assertEquals(expectedSubtraction, gameTwo.getAnswer());
        assertNotEquals(unexpectedAnswer, gameTwo.getAnswer());
    }

    @Test
    public void setAnswer() {
        int expectedAnswer = 15;
        gameOne.setAnswer(expectedAnswer);

        int actualAnswer = gameOne.getAnswer();
        assertEquals(expectedAnswer, actualAnswer);
        assertNotEquals(14, actualAnswer);

        int expectedAnswerSub = 10;
        gameTwo.setAnswer(expectedAnswerSub);

        int actualAnswerSub = gameTwo.getAnswer();
        assertEquals(expectedAnswerSub, actualAnswerSub);
        assertNotEquals(15, actualAnswerSub);

        int expectedAnswerMul = 30;
        gameThree.setAnswer(expectedAnswerMul);

        int actualAnswerMul = gameThree.getAnswer();
        assertEquals(expectedAnswerMul, actualAnswerMul);
        assertNotEquals(0, actualAnswerMul);

        int expectedAnswerDiv = 2;
        gameFour.setAnswer(expectedAnswerDiv);

        int actualAnswerDiv = gameFour.getAnswer();
        assertEquals(expectedAnswerDiv, actualAnswerDiv);
        assertNotEquals(0, actualAnswerDiv);
    }
}