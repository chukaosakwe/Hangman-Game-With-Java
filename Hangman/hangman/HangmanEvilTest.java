package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HangmanEvilTest {

    /**
     * test update game word
     */
    @Test
    void testUpdateGameWord() {
        //1st test
        ReadParseDictionary testDictionary = new ReadParseDictionary();

        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy2");
        testDictionary.setCleanWordList(cleanWordList1);

        HangmanEvil test = new HangmanEvil(testDictionary);
        test.setMostRecentGuess("e");

        List<String> wordFamily = new ArrayList<>();
        wordFamily.add("heel");
        wordFamily.add("here");
        wordFamily.add("hair");
        wordFamily.add("hint");
        wordFamily.add("hate");
        wordFamily.add("hike");
        wordFamily.add("hope");

        test.setSameWordFamily(wordFamily);

        // test game word
        //System.out.println(test.getSameWordFamily());
        test.updateGameWord();
        assertTrue(test.getGameWord().equals("hike") || test.getGameWord().equals("hate") || test.getGameWord().equals("hope"));
        assertFalse(test.getGameWord().equals("heel"));
        assertFalse(test.getGameWord().equals("hair"));
        assertFalse(test.getGameWord().equals("hint"));

        // test same word family
        List<String> sameWordFamilyTest = new ArrayList<>();
        sameWordFamilyTest.add("hate");
        sameWordFamilyTest.add("hike");
        sameWordFamilyTest.add("hope");
        test.setSameWordFamily(sameWordFamilyTest);
        assertEquals(sameWordFamilyTest,test.getSameWordFamily());

        // test partition key
        List<String> testPartitionKey1= new ArrayList<>();
        testPartitionKey1.add("*");
        testPartitionKey1.add("*");
        testPartitionKey1.add("*");
        testPartitionKey1.add("e");
        assertEquals(testPartitionKey1, test.getLastPartitionKey());

        // 2nd test
        HangmanEvil test2 = new HangmanEvil(testDictionary);
        test2.setMostRecentGuess("t");

        List<String> wordFamily2 = new ArrayList<>();
        wordFamily2.add("heel");
        wordFamily2.add("here");
        wordFamily2.add("hair");
        wordFamily2.add("hint");
        wordFamily2.add("hate");
        wordFamily2.add("hike");
        wordFamily2.add("hope");

        test2.setSameWordFamily(wordFamily2);
        test2.updateGameWord();
        // test game word
        assertTrue(test2.getGameWord().equals("hint") || test2.getGameWord().equals("hate"));

        // test same word family update
        List<String> sameWordFamilyTest2 = new ArrayList<>();
        sameWordFamilyTest2.add("hate");
        assertEquals(sameWordFamilyTest2,test2.getSameWordFamily());

        // test last partition key update
        List<String> testPartitionKey2= new ArrayList<>();
        testPartitionKey2.add("*");
        testPartitionKey2.add("*");
        testPartitionKey2.add("t");
        testPartitionKey2.add("*");
        assertEquals(testPartitionKey2, test2.getLastPartitionKey());
    }

    /**
     * test create partition key
     */
    @Test
    void testCreatePartitionKey() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();
        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);
        HangmanEvil test = new HangmanEvil(testDictionary);

        String[] correctLetters= new String[4];
        correctLetters[0] = "h";
        correctLetters[1]="e";
        test.setCorrectLetters(correctLetters);

        List<String> heel = new ArrayList<>();
        heel.add("h");
        heel.add("e");
        heel.add("e");
        heel.add("*");
        assertEquals(heel,test.createPartitionKey("heel"));

        List<String> here = new ArrayList<>();
        here.add("h");
        here.add("e");
        here.add("*");
        here.add("e");
        assertEquals(here,test.createPartitionKey("here"));

        List<String> hair = new ArrayList<>();
        hair.add("h");
        hair.add("*");
        hair.add("*");
        hair.add("*");
        assertEquals(hair,test.createPartitionKey("hair"));

        List<String> hate = new ArrayList<>();
        hate.add("h");
        hate.add("*");
        hate.add("*");
        hate.add("e");
        assertEquals(hate,test.createPartitionKey("hate"));
    }

    /**
     * test word partition
     */
    @Test
    void testWordPartition() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();
        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy");
        testDictionary.setCleanWordList(cleanWordList1);
        HangmanEvil test = new HangmanEvil(testDictionary);
        test.setMostRecentGuess("e");

        String[] correctLetters= new String[4];
        correctLetters[0] = "h";
        correctLetters[1]="e";
        correctLetters[2]="*";
        correctLetters[3]="*";
        test.setCorrectLetters(correctLetters);

        List<String> wordFamily = new ArrayList<>();
        wordFamily.add("heel");
        wordFamily.add("here");
        wordFamily.add("hair");
        wordFamily.add("hint");
        wordFamily.add("hate");
        wordFamily.add("hike");
        wordFamily.add("hope");

        test.setSameWordFamily(wordFamily);
        test.wordPartition();
        List<String> partitionKey1= new LinkedList<>();
        partitionKey1.add("h");
        partitionKey1.add("e");
        partitionKey1.add("e");
        partitionKey1.add("*");

        List<String> partitionKey2= new LinkedList<>();
        partitionKey2.add("h");
        partitionKey2.add("e");
        partitionKey2.add("*");
        partitionKey2.add("e");

        List<String> partitionKey3= new LinkedList<>();
        partitionKey3.add("h");
        partitionKey3.add("*");
        partitionKey3.add("*");
        partitionKey3.add("*");

        List<String> partitionKey4= new LinkedList<>();
        partitionKey4.add("h");
        partitionKey4.add("*");
        partitionKey4.add("*");
        partitionKey4.add("e");

        List<String> value1= new LinkedList<>();
        value1.add("heel");

        List<String> value2= new LinkedList<>();
        value2.add("here");

        List<String> value3= null;

        List<String> value4= new ArrayList<>();
        value4.add("hate");
        value4.add("hike");
        value4.add("hope");

        assertEquals(value1,test.getListOfWordFamilies().get(partitionKey1));
        assertEquals(value2,test.getListOfWordFamilies().get(partitionKey2));
        assertEquals(value3,test.getListOfWordFamilies().get(partitionKey3));
        assertEquals(value4,test.getListOfWordFamilies().get(partitionKey4));

    }

    /**
     * test update correct letters
     */
    @Test
    void testUpdateCorrectLetters() {
        //1st test
        ReadParseDictionary testDictionary = new ReadParseDictionary();

        List<String> cleanWordList1 = new ArrayList<>();
        cleanWordList1.add("dummy");
        cleanWordList1.add("dummy2");
        testDictionary.setCleanWordList(cleanWordList1);

        HangmanEvil test = new HangmanEvil(testDictionary);
        test.setMostRecentGuess("e");

        List<String> wordFamily = new ArrayList<>();
        wordFamily.add("heel");
        wordFamily.add("here");
        wordFamily.add("hair");
        wordFamily.add("hint");
        wordFamily.add("hate");
        wordFamily.add("hike");
        wordFamily.add("hope");

        test.setSameWordFamily(wordFamily);

        test.updateGameWord();
        test.updateCorrectLetters();

        String[] expectedResult = new String[4];
        expectedResult[0] = "*";
        expectedResult[1] = "*";
        expectedResult[2] = "*";
        expectedResult[3] = "e";
        assertArrayEquals(expectedResult,test.getCorrectLetters());

        // 2nd test
        HangmanEvil test2 = new HangmanEvil(testDictionary);
        test2.setMostRecentGuess("t");

        List<String> wordFamily2 = new ArrayList<>();
        wordFamily2.add("eel");
        wordFamily2.add("air");
        wordFamily2.add("kit");
        wordFamily2.add("hat");
        wordFamily2.add("hop");


        test2.setSameWordFamily(wordFamily2);
        test2.updateGameWord();
        test2.updateCorrectLetters();
        String[] expectedResult2 = new String[3];
        expectedResult2[0] = "*";
        expectedResult2[1] = "*";
        expectedResult2[2] = "t";
        assertArrayEquals(expectedResult2,test2.getCorrectLetters());

    }

    /**
     * test initialize same word family
     */
    @Test
    void testInitializeSameWordFamily() {
        ReadParseDictionary testDictionary = new ReadParseDictionary();

        List<String> cleanWordList = new ArrayList<>();
        cleanWordList.add("one");
        cleanWordList.add("two");
        cleanWordList.add("dove");
        cleanWordList.add("four");
        cleanWordList.add("kit");

        testDictionary.setCleanWordList(cleanWordList);

        HangmanEvil testHangman = new HangmanEvil(testDictionary);
        testHangman.setGameWord("two");

        List<String> testSameWordFamily = new ArrayList<>();
        testSameWordFamily.add("one");
        testSameWordFamily.add("two");
        testSameWordFamily.add("kit");

        testHangman.initializeSameWordFamily();
        assertEquals(testSameWordFamily, testHangman.getSameWordFamily());

        // 2nd test
        testHangman.setGameWord("dove");

        List<String> testSameWordFamily2 = new ArrayList<>();
        testSameWordFamily2.add("dove");
        testSameWordFamily2.add("four");

        testHangman.initializeSameWordFamily();
        assertEquals(testSameWordFamily2, testHangman.getSameWordFamily());


        // 3rd test
        testHangman.setGameWord("four");
        testHangman.initializeSameWordFamily();
        assertEquals(testSameWordFamily2, testHangman.getSameWordFamily());
        List<String> testLastPartitionKey = new ArrayList<>();

        // test create last partition key
        for (int i=0; i<testHangman.getGameWord().length();i++) {
            testLastPartitionKey.add("*");
        }

        assertEquals(testLastPartitionKey,testHangman.getLastPartitionKey());


    }
}