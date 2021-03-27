package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Hangman {
    /**
     * stores the word the player must guess
      */
    private String gameWord;

    /**
     *stores the letters the player has guessed wrongly so far
     */
    private List<String> incorrectLetters;

    /**
     * stores instance of this dictionary
     */
    private ReadParseDictionary dictionary;

    /**
     * stores the letters the player has guessed correctly so far
     */
    private String[] correctLetters;

    /**
     * stores the most recent human guess
     */
    private String mostRecentGuess;

    /**
     *  keeps track of whether the game is over. True means it is over.
     */
    private boolean isGameOver;

    /**
     * creates instance of hangman
     * @param dictionary
     */
    public Hangman(ReadParseDictionary dictionary){
        this.dictionary=dictionary;
        incorrectLetters = new ArrayList<>();
        isGameOver = false;
    }

    /**
     * print start instructions for the game
     */
    public void printStartInstruction(){
        System.out.println("Welcome to Hangman!");
        System.out.println("Guess enough letters until you guess the full word");
        System.out.println("Try to complete the word with as few guesses as possible");
        printInterimResults();
    }

    /**
     * generate a random word and set the game word to this
     * also initializes correct letters list
     */
    public void pickInitialWord(){
        // generate and set initial word from cleanWordList
        List<String> cleanWordList = dictionary.getCleanWordList();
        String randWord = generateRandomWord(cleanWordList);
        setGameWord(randWord);

        // initialize correct letters list
        String[] correctLetters = new String[randWord.length()];
        for(int i=0; i<gameWord.length();i++) {
            correctLetters[i]="*";
        }
        this.correctLetters = correctLetters;
    }

    /**
     * plays a turn of the human player
     * @param scanner to use to read user input
     */
    public void playTurn(Scanner scanner){
        String recentGuess = readLetterInput(scanner);
        isLetterCorrect(recentGuess);
        printInterimResults();
        hasGameEnded();
    }
    /**
     * returns true if the guessed letter is correctly
     * @param guessedLetter is user's guess
     * @return true if guessed correct
     */
    public boolean isLetterCorrect(String guessedLetter){
        if(gameWord.contains(guessedLetter)){
            updateCorrectLetters();
            return true;
        }
        else {
            incorrectLetters.add(guessedLetter);
            return false;
        }
    }

    /**
     * print interim results and next step
     */
    public void printInterimResults(){
        System.out.println("----------------------------");
        System.out.println("Guess a letter");
        System.out.println("----------------------------");
        System.out.println(correctLettersString());
        System.out.println("----------------------------");
        System.out.println("Incorrect guesses: "+ incorrectLettersString());

        int totalGuesses = incorrectLetters.size() + noCorrectLettersGuessed();
        System.out.println("Total guesses: "+ totalGuesses);
    }

    /**
     * counts the number of letters guessed correctly
     * @return integer of number of correct guesses
     */
    public int noCorrectLettersGuessed(){
        int count = 0;
        for(String letter: correctLetters){
            if(letter!="*"){
                count+=1;
            }
        }
        return count;
    }
    /**
     * Generates string of correct letters
     * @return concatenated string of letters
     */
    public String correctLettersString(){
        String correctLetters = "";

        // concatenate correctly guessed letters to the string
        for(int i=0; i<getCorrectLetters().length; i++){

            String letter = getCorrectLetters()[i];
            correctLetters += letter;
        }

        return correctLetters;
    }

    /**
     * Generates string of incorrect letters
     * @return concatenated string of letters
     */
    public String incorrectLettersString(){
        String incorrectLetters = "[";
        // concatenate incorrectly guessed letters to the string
        for(int i=0; i<getIncorrectLetters().size(); i++){

            // add comma between letters
            if(i>=1) {incorrectLetters += ", ";}

            String letter = getIncorrectLetters().get(i);
            incorrectLetters += letter;
        }

        incorrectLetters += "]";
        return incorrectLetters;
    }

    /**
     * update the correct letters array variable
     */
    public abstract void updateCorrectLetters();

    /**
     * checks if game has ended
     * @return true if game has ended
     */
    public boolean hasGameEnded(){
        String completeWord = "";
        // combine list of correct letters to a word
        for(String letter: correctLetters){
            completeWord+=letter;
        }

        // check if guessed words equals gameword
        if(completeWord.equals(gameWord)){
            isGameOver = true;
            System.out.println("----------------------------");
            System.out.println("Game Over. You've guessed the word!");

            // print version of the game the person was playing
            System.out.println("There are two versions of the game");
            if(this instanceof HangmanEvil) System.out.println("You were playing the Evil (and harder) version of this game");
            if(this instanceof HangmanTraditional) System.out.println("You were playing the traditional (and easier) version of this game");

            return true;
        }
        else {return false;}

    }


    /**
     * Asks user if would like to play again
     * returns 1 if yes and 0 if no
     * @param scanner used to scan user input
     */
    public int playAgain(Scanner scanner){
        System.out.println("Would you like to play again? Enter 'y' for yes or 'n' for no");

        // strip spaces and change to lower case
        String response= scanner.next().strip().toLowerCase();

        // keep asking for input until receive  valid response
        while (!(response.equals("y")) && !(response.equals("n"))){
            System.out.println("Please enter 'y' for yes or 'n' for no");
            // strip spaces and change to lower case
            response = scanner.next().strip().toLowerCase();
        }
        int playAgain;
        if (response == "y"){
            playAgain=1;
            return playAgain;}
        else{
            playAgain =0;
            return playAgain;
        }

    }

    /**
     * Asks user for letter input until received
     * @param scanner used to scan user input
     * @return guess as a string
     */
    public String readLetterInput(Scanner scanner){

        // strip spaces and change to lower case
        String guess= scanner.next().strip().toLowerCase();

        // character version of the guess letter
        char guessChar = guess.charAt(0);

        // keep asking for input until receive a letter
        while (guess.length()>1 || !(Character.isLetter(guessChar)) || containsCorrectLetter(guess) || incorrectLetters.contains(guess) ){

            if(containsCorrectLetter(guess) || incorrectLetters.contains(guess)){
                System.out.println("You've already guessed this before");
            }

            System.out.println("Please provide a valid letter");

            // strip spaces and change to lower case
            guess = scanner.next().strip().toLowerCase();

        }
        setMostRecentGuess(guess);

        return guess;
    }

    /**
     * generate a random word from the input list
     * @return random word as a string
     */
    protected String generateRandomWord(List<String> wordList){

        // return null if list is empty
        if(wordList== null){return null;}

        // generate random index in the word list
        int randomNum= HangmanGameSupport.randomInt(0,wordList.size()-1);

        // retrieve the word
        String randWord = wordList.get(randomNum);
        return randWord;
    }

    /**
     * checks if the input letter is in the correct letters array variable
     * @param letter is letter to check if is one of correctly guessed letter
     * @return true if letter is present
     */
    public boolean containsCorrectLetter(String letter){
        for(int i=0; i<correctLetters.length; i++){
            // if array is empty, return false
            if(correctLetters[i]== null){
                return false;
            }
            // return true if letter is found
            if(correctLetters[i].equals(letter)){
                return true;
            }
        }
        return false;
    }

    /**
     * get game word
     * @return gameWord
     */
    public String getGameWord() {
        return gameWord;
    }

    /**
     * get incorrect letters
     * @return incorrect letters
     */
    public List<String> getIncorrectLetters() {
        return incorrectLetters;
    }

    /**
     * get correct letters
     * @return correct letters
     */
    public String[] getCorrectLetters() {
        return correctLetters;
    }

    /**
     * get dictionary
     * @return dictionary
     */
    public ReadParseDictionary getDictionary() {
        return dictionary;
    }

    /**
     *get most recent guess
     * @return most recent letter guess
     */
    public String getMostRecentGuess() {
        return mostRecentGuess;
    }

    /**
     *
     * @return true if game is over
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     *  set game word
     * @param gameWord as input to set game word
     */
    public void setGameWord(String gameWord) {
        this.gameWord = gameWord;
    }

    /**
     *  set incorrect letters
     * @param incorrectLetters as input to set incorrect letters
     */
    public void setIncorrectLetters(List<String> incorrectLetters) {
        this.incorrectLetters = incorrectLetters;
    }

    /**
     * set correct letters
     * @param correctLetters as input to set correct letters
     */
    public void setCorrectLetters(String[] correctLetters) {
        this.correctLetters = correctLetters;
    }

    /**
     * set dictionary
     * @param dictionary as input to set
     */
    public void setDictionary(ReadParseDictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * set game over
     * @param gameOver as input to set
     */
    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    /**
     * set most recent gues
     * @param mostRecentGuess as input to set
     */
    public void setMostRecentGuess(String mostRecentGuess) {
        this.mostRecentGuess = mostRecentGuess;
    }
}
