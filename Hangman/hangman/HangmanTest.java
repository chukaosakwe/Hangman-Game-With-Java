package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HangmanTest {

    /**
     * test pick initial word
     */
    @Test
    void testPickInitialWord() {
        // test pick initial word
        ReadParseDictionary testDictionary = new ReadParseDictionary();

        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("one");
        cleanWordList1.add("two");

        testDictionary.setCleanWordList(cleanWordList1);

        Hangman testHangman = new HangmanTraditional(testDictionary);

        testHangman.pickInitialWord();
        assertTrue(testHangman.getGameWord().equals("one") ||testHangman.getGameWord().equals("two") );

        cleanWordList1.add("three");
        testHangman.pickInitialWord();
        assertTrue(testHangman.getGameWord().equals("one") ||testHangman.getGameWord().equals("two") || testHangman.getGameWord().equals("three"));
    }

    /**
     * test if letter is correct
     */
    @Test
    void testIsLetterCorrect() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();
        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);
        Hangman testHangman = new HangmanTraditional(testDictionary);
        testHangman.setGameWord("too");
        testHangman.setMostRecentGuess("t");
        assertTrue(testHangman.isLetterCorrect("t"));
        testHangman.setMostRecentGuess("o");
        assertTrue(testHangman.isLetterCorrect("o"));
        testHangman.setMostRecentGuess("a");
        assertFalse(testHangman.isLetterCorrect("a"));


    }

    /**
     * test if game has ended
     */
    @Test
    void testHasGameEnded() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();
        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);
        Hangman testHangman = new HangmanTraditional(testDictionary);
        testHangman.setGameWord("too");
        String[] correctLetters = new String[3];
        correctLetters[0] = "t";
        correctLetters[1]= "o";
        testHangman.setCorrectLetters(correctLetters);

        // test game has not ended
        assertFalse(testHangman.hasGameEnded());
        assertFalse(testHangman.isGameOver());

        // test game has ended
        correctLetters[2]="o";
        testHangman.setCorrectLetters(correctLetters);
        assertTrue(testHangman.hasGameEnded());
        assertTrue(testHangman.isGameOver());

    }

    /**
     * test contains correct letter
     */
    @Test
    void testContainsCorrectLetter() {
        String[] correctLetters = new String[3];
        ReadParseDictionary testDictionary = new ReadParseDictionary();
        correctLetters[0]= "o";
        correctLetters[1]="n";
        correctLetters[2] = "e";

        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("one");
        cleanWordList1.add("two");
        testDictionary.setCleanWordList(cleanWordList1);

        Hangman testHangman = new HangmanTraditional(testDictionary);
        testHangman.setCorrectLetters(correctLetters);
        assertTrue(testHangman.containsCorrectLetter("o"));
        assertTrue(testHangman.containsCorrectLetter("n"));
        assertTrue(testHangman.containsCorrectLetter("e"));
        assertFalse(testHangman.containsCorrectLetter("f"));
    }

    /**
     * test generate random word
     */
    @Test
    void testGenerateRandomWord() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();


        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);

        Hangman testHangman = new HangmanTraditional(testDictionary);
        List<String> sameWordFamily = new ArrayList<>();
        sameWordFamily.add("one");
        sameWordFamily.add("two");
        String randWord = testHangman.generateRandomWord(sameWordFamily);
        assertTrue(randWord.equals("one")|| randWord.equals("two"));

    }

    /**
     * test incorrectletters string
     */
    @Test
    void testIncorrectLettersString() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();


        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);

        // test 1 letter
        Hangman testHangman = new HangmanTraditional(testDictionary);
        List<String> testIncorrectLetters = new ArrayList<>();
        testIncorrectLetters.add("a");
        testHangman.setIncorrectLetters(testIncorrectLetters);
        assertEquals("[a]", testHangman.incorrectLettersString());

        // test multiple letters
        List<String> testIncorrectLetters2 = new ArrayList<>();
        testIncorrectLetters2.add("a");
        testIncorrectLetters2.add("n");
        testIncorrectLetters2.add("t");
        testHangman.setIncorrectLetters(testIncorrectLetters2);
        assertEquals("[a, n, t]", testHangman.incorrectLettersString());
    }

    /**
     * test correct letters string
     */
    @Test
    void testCorrectLettersString() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();


        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);

        // test 1
        Hangman testHangman = new HangmanTraditional(testDictionary);
        String[] testCorrectLetters = new String[4];
        testCorrectLetters[0]= "*";
        testCorrectLetters[1]= "*";
        testCorrectLetters[2]= "*";
        testCorrectLetters[3]= "*";
        testHangman.setCorrectLetters(testCorrectLetters);

        String expectedResult = "****";

        assertEquals(expectedResult, testHangman.correctLettersString());

        // test 2
        String[] testCorrectLetters2 = new String[4];
        testCorrectLetters2[0]= "*";
        testCorrectLetters2[1]= "*";
        testCorrectLetters2[2]= "*";
        testCorrectLetters2[3]= "t";
        testHangman.setCorrectLetters(testCorrectLetters2);

        String expectedResult2 = "***t";

        assertEquals(expectedResult2, testHangman.correctLettersString());
    }


    /**
     * test no correct letters guessed
     */
    @Test
    void noCorrectLettersGuessed() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();


        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);

        // test 1
        Hangman testHangman = new HangmanTraditional(testDictionary);
        String[] correctLetters1 = new String[3];
        correctLetters1[0] = "*";
        correctLetters1[1] = "*";
        correctLetters1[2] = "*";

        testHangman.setCorrectLetters(correctLetters1);
        assertEquals(0, testHangman.noCorrectLettersGuessed());

        // test 2
        correctLetters1[0] = "*";
        correctLetters1[1] = "r";
        correctLetters1[2] = "e";

        testHangman.setCorrectLetters(correctLetters1);
        assertEquals(2, testHangman.noCorrectLettersGuessed());
    }


}