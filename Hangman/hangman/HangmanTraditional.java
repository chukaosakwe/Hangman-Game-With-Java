package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;

public class HangmanTraditional extends Hangman{

    /**
     * creates new instance of traditional Hangman game with input dictionary
     */
    public HangmanTraditional(ReadParseDictionary dictionary){
        super(dictionary);
        pickInitialWord();
    }

    /**
     * update correct letters using index of correctly guessed letter in game word
     */
    @Override
    public void updateCorrectLetters() {
        String gameWord = getGameWord();
        String[] correctLetters = getCorrectLetters();

        for (int i=0; i<getGameWord().length();i++){
            // store this letter at this index of the game word
            String letter = Character.toString(gameWord.charAt(i));
            if(letter.equals(getMostRecentGuess())){
                correctLetters[i] = getMostRecentGuess();
            }
        }

        setCorrectLetters(correctLetters);
    }
}
