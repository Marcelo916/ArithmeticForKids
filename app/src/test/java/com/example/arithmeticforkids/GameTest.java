package com.example.arithmeticforkids;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertNotNull(gameOne.getQuestions());
        assertEquals(0, gameOne.getCorrect());
        assertEquals(0, gameOne.getIncorrect());
        assertEquals(0, gameOne.getTotalQuestions());
        assertEquals(0, gameOne.getScore());
        assertNotNull(gameOne.getCurrentQuestion());
        assertEquals("addition", gameOne.getOperation());
        assertNotEquals("subtraction", gameOne.getOperation());
        assertNotNull(gameOne.getOperation());
        assertNotNull(gameOne);

        assertNotNull(gameTwo.getQuestions());
        assertEquals(0, gameTwo.getCorrect());
        assertEquals(0, gameTwo.getIncorrect());
        assertEquals(0, gameTwo.getTotalQuestions());
        assertEquals(0, gameTwo.getScore());
        assertNotNull(gameTwo.getCurrentQuestion());
        assertEquals("subtraction", gameTwo.getOperation());
        assertNotEquals("addition", gameTwo.getOperation());
        assertNotNull(gameTwo.getOperation());
        assertNotNull(gameTwo);

        assertNotNull(gameThree.getQuestions());
        assertEquals(0, gameThree.getCorrect());
        assertEquals(0, gameThree.getIncorrect());
        assertEquals(0, gameThree.getTotalQuestions());
        assertEquals(0, gameThree.getScore());
        assertNotNull(gameThree.getCurrentQuestion());
        assertEquals("multiplication", gameThree.getOperation());
        assertNotEquals("subtraction", gameThree.getOperation());
        assertNotNull(gameThree.getOperation());
        assertNotNull(gameThree);

        assertNotNull(gameFour.getQuestions());
        assertEquals(0, gameFour.getCorrect());
        assertEquals(0, gameFour.getIncorrect());
        assertEquals(0, gameFour.getTotalQuestions());
        assertEquals(0, gameFour.getScore());
        assertNotNull(gameFour.getCurrentQuestion());
        assertEquals("division", gameFour.getOperation());
        assertNotEquals("subtraction", gameFour.getOperation());
        assertNotNull(gameFour.getOperation());
        assertNotNull(gameFour);
    }

    @Test
    public void newQuestion() {
        Game game = new Game("addition");
        int initialQuestionCount = game.getQuestions().size();
        game.newQuestion();
        assertEquals(initialQuestionCount + 1, game.getQuestions().size());
        assertEquals(initialQuestionCount + 1, game.getTotalQuestions());

        gameOne.newQuestion();
        assertEquals(1, gameOne.getTotalQuestions());
    }

    @Test
    public void checkAnswer() {
        Game game = new Game("+");
        assertFalse(game.checkAnswer(0));  // assuming 0 is incorrect
        assertEquals(-10, game.getScore());
        assertEquals(1, game.getIncorrect());

        gameOne.newQuestion();
        int correctAnswer = gameOne.getCurrentQuestion().getAnswer();
        assertTrue(gameOne.checkAnswer(correctAnswer));
        assertEquals(1, gameOne.getCorrect());
        assertEquals(0, gameOne.getIncorrect());
        assertFalse(gameOne.checkAnswer(correctAnswer + 1));
        assertEquals(1, gameOne.getCorrect());
        assertEquals(1, gameOne.getIncorrect());
    }

    @Test
    public void getScore() {
        Game game = new Game("addition");
        assertEquals(0, game.getScore());

        gameOne.checkAnswer(gameOne.getCurrentQuestion().getAnswer());
        gameOne.checkAnswer(gameOne.getCurrentQuestion().getAnswer());
        assertEquals(20, gameOne.getScore());

        gameTwo.checkAnswer(gameTwo.getCurrentQuestion().getAnswer() + 1);
        gameTwo.checkAnswer(gameTwo.getCurrentQuestion().getAnswer() + 1);
        assertEquals(-20, gameTwo.getScore());

        gameThree.checkAnswer(gameThree.getCurrentQuestion().getAnswer());
        gameThree.checkAnswer(gameThree.getCurrentQuestion().getAnswer() + 1);
        assertEquals(0, gameThree.getScore());
    }

    @Test
    public void setScore() {
        gameOne.setScore(50);
        assertEquals(50, gameOne.getScore());
        assertNotEquals(10, gameOne.getScore());
        gameOne.setScore(0);
        assertEquals(0, gameOne.getScore());
        assertNotEquals(20, gameOne.getScore());
        gameOne.setScore(-30);
        assertEquals(-30, gameOne.getScore());
        assertNotEquals(10, gameOne.getScore());

        gameThree.setScore(240);
        assertEquals(240, gameThree.getScore());
        assertNotEquals(100, gameThree.getScore());
        gameThree.setScore(0);
        assertEquals(0, gameThree.getScore());
        assertNotEquals(120, gameThree.getScore());
        gameThree.setScore(-20);
        assertEquals(-20, gameThree.getScore());
        assertNotEquals(10, gameThree.getScore());
    }

}