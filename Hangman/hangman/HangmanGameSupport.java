package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;
import com.company.Hangman.hangman.Hangman;
import com.company.Hangman.hangman.HangmanEvil;
import com.company.Hangman.hangman.HangmanTraditional;

import java.util.Random;

public class HangmanGameSupport {

    /**
     * generates a random integer in the specified range
     * @param minimum
     * @param maximum
     * @return random integer between minimum and maximum
     */
    public static int randomInt(int minimum, int maximum){
        Random rand = new Random();
        int randomNumber = rand.nextInt((maximum - minimum) + 1) + minimum;
        return randomNumber;
    }

    /**
     * choose version of game to play and return the relevant instance
     * @param dictionary
     * @return
     */
    public static Hangman chooseVersion(ReadParseDictionary dictionary){
        Hangman hangman;
        int randInt = randomInt(0,1);
        if(randInt == 0){
            hangman = new HangmanTraditional(dictionary);
        }
        else{
            hangman = new HangmanEvil(dictionary);
        }
        return hangman;
    }
}
