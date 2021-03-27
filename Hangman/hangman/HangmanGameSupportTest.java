package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;
import com.company.Hangman.hangman.Hangman;
import com.company.Hangman.hangman.HangmanEvil;
import com.company.Hangman.hangman.HangmanGameSupport;
import com.company.Hangman.hangman.HangmanTraditional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HangmanGameSupportTest {

    /**
     * test choose version
     * @throws Exception if input file not found
     */
    @Test
    void testChooseVersion() throws Exception {
        // read the dictionary and extract clean words
        ReadParseDictionary dictionary = new ReadParseDictionary();
        dictionary.readAllWords("C:\\Users\\chuka\\IdeaProjects\\Projects 591 Java\\src\\com\\company\\Hangman\\dictionary\\words.txt");
        dictionary.readCleanWords();

        // decide to play traditional or evil hangman randomly
        Hangman hangman= HangmanGameSupport.chooseVersion(dictionary);

        assertTrue(hangman instanceof HangmanEvil || hangman instanceof HangmanTraditional);
    }

    /**
     * test random int
     * @throws Exception if file not found
     */
    @Test
    void testRandomInt() throws Exception {

            // read the dictionary and extract clean words
            ReadParseDictionary dictionary = new ReadParseDictionary();
            dictionary.readAllWords("C:\\Users\\chuka\\IdeaProjects\\Projects 591 Java\\src\\com\\company\\Hangman\\dictionary\\words.txt");
            dictionary.readCleanWords();


            // test 1
            Hangman testHangman = new HangmanTraditional(dictionary);
            int randInt = HangmanGameSupport.randomInt(1,2);
            assertTrue(randInt==1 || randInt ==2);

            //test 2
            int randInt2 = HangmanGameSupport.randomInt(2,4);
            assertTrue(randInt2== 2|| randInt2 ==3 || randInt2 ==4);
    }
}