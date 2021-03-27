package com.company.Hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;
import com.company.Hangman.hangman.Hangman;
import com.company.Hangman.hangman.HangmanGameSupport;

import java.util.Scanner;

/**
 * @author Chukwuka Osakwe
 */
public class HangmanGameController {

    public static void main(String[] args) throws Exception {

        // read the dictionary and extract clean words
        ReadParseDictionary dictionary = new ReadParseDictionary();
        dictionary.readAllWords("C:\\Users\\chuka\\IdeaProjects\\Projects 591 Java\\src\\com\\company\\Hangman\\dictionary\\words.txt");
        dictionary.readCleanWords();

        // decide to play traditional or evil hangman randomly
        Hangman hangman= HangmanGameSupport.chooseVersion(dictionary);

        // print start instructions
        hangman.printStartInstruction();
        Scanner scanner = new Scanner(System.in);
        boolean continueGame= true;
        int playAgain=0;// used to track if the player would like to play again

        // runs the gameplay
        while(continueGame){

            hangman.playTurn(scanner);

            // checks if game is over and asks user if would like to replay
            if(hangman.isGameOver()){
                playAgain = hangman.playAgain(scanner);
                if(playAgain == 0){
                   hangman = HangmanGameSupport.chooseVersion(dictionary);
                    hangman.printStartInstruction();
                }
                else{
                    continueGame=false;
                }
            }

        }





    }
}
