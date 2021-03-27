package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HangmanTraditionalTest {

    /**
     * test update correct letters
     */
    @Test
    void testUpdateCorrectLetters() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();
        List<String> cleanWordList = new ArrayList<>();
        cleanWordList.add("dummy");
        testDictionary.setCleanWordList(cleanWordList);

        Hangman testHangman = new HangmanTraditional(testDictionary);
        testHangman.setGameWord("one");

        // test 1
        testHangman.setMostRecentGuess("n");


        String[] correctLetters = new String[3];
        correctLetters[0] = "*";
        correctLetters[1] = "*";
        correctLetters[2] = "*";

        testHangman.setCorrectLetters(correctLetters);
        testHangman.updateCorrectLetters();

        String[] expectedCorrectLetters = new String[3];
        expectedCorrectLetters[0] = "*";
        expectedCorrectLetters[1] = "n";
        expectedCorrectLetters[2] = "*";

        String [] actualCorrectLetters = testHangman.getCorrectLetters();

        assertArrayEquals(expectedCorrectLetters,actualCorrectLetters);

        // test 2
        testHangman.setMostRecentGuess("o");
        testHangman.updateCorrectLetters();
        String[] expectedCorrectLetters2 = new String[3];
        expectedCorrectLetters2[0] = "o";
        expectedCorrectLetters2[1] = "n";
        expectedCorrectLetters2[2] = "*";

        String [] actualCorrectLetters2 = testHangman.getCorrectLetters();

        assertArrayEquals(expectedCorrectLetters2,actualCorrectLetters2);

        // test 3
        testHangman.setMostRecentGuess("e");
        testHangman.updateCorrectLetters();
        String[] expectedCorrectLetters3 = new String[3];
        expectedCorrectLetters3[0] = "o";
        expectedCorrectLetters3[1] = "n";
        expectedCorrectLetters3[2] = "e";

        String [] actualCorrectLetters3 = testHangman.getCorrectLetters();

        assertArrayEquals(expectedCorrectLetters3,actualCorrectLetters3);

        // test 4
        testHangman.setGameWord("too");
        testHangman.setMostRecentGuess("o");

        correctLetters[0] = "*";
        correctLetters[1] = "*";
        correctLetters[2] = "*";

        testHangman.setCorrectLetters(correctLetters);
        testHangman.updateCorrectLetters();

        expectedCorrectLetters[0] = "*";
        expectedCorrectLetters[1] = "o";
        expectedCorrectLetters[2] = "o";

        actualCorrectLetters = testHangman.getCorrectLetters();

        assertArrayEquals(expectedCorrectLetters,actualCorrectLetters);

    }
}