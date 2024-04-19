package com.example.arithmeticforkids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
    private Game gameOne;
    private Game gameTwo;
    private Game gameThree;
    private Game gameFour;

    @Before
    public void setUp() throws Exception {
        gameOne = new Game("addition");
        gameTwo = new Game("subtraction");
        gameThree = new Game("multiplication");
        gameFour = new Game("division");
    }

    @After
    public void tearDown() throws Exception {
        gameOne = null;
        gameTwo = null;
        gameThree = null;
        gameFour = null;
    }

    @Test
    public void Game() throws Exception {
        //Game game = new Game("+");
        assertNotNull(gameOne.getQuestions());
        assertEquals(0, gameOne.getCorrect());
        assertEquals(0, gameOne.getIncorrect());
        assertEquals(0, gameOne.getTotalQuestions());
        assertEquals(0, gameOne.getScore());
        assertNotNull(gameOne.getCurrentQuestion());

        assertNotNull(gameTwo.getQuestions());
        assertEquals(0, gameTwo.getCorrect());
        assertEquals(0, gameTwo.getIncorrect());
        assertEquals(0, gameTwo.getTotalQuestions());
        assertEquals(0, gameTwo.getScore());
        assertNotNull(gameTwo.getCurrentQuestion());
    }

    @Test
    public void newQuestion() {
        Game game = new Game("+");
        game.newQuestion();
        assertEquals(1, game.getTotalQuestions());
        //assertEquals(2, game.getQuestions().size());  // Include initial question
        //assertEquals(15, game.getCurrentQuestion().getAnswer());
    }

    @Test
    public void checkAnswer() {
        Game game = new Game("+");
        assertFalse(game.checkAnswer(0));  // assuming 0 is incorrect
        assertEquals(-10, game.getScore());
        assertEquals(1, game.getIncorrect());
    }

    @Test
    public void getQuestions() {

    }

    @Test
    public void setQuestions() {
    }

    @Test
    public void getCorrect() {
    }

    @Test
    public void setCorrect() {
    }

    @Test
    public void getIncorrect() {
    }

    @Test
    public void setIncorrect() {
    }

    @Test
    public void getTotalQuestions() {
    }

    @Test
    public void setTotalQuestions() {
    }

    @Test
    public void getScore() {
    }

    @Test
    public void setScore() {
    }

    @Test
    public void getCurrentQuestion() {
    }

    @Test
    public void setCurrentQuestion() {
    }
}