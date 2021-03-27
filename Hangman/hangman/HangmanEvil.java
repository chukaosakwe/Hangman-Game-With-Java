package com.company.Hangman.hangman;

import com.company.Hangman.dictionary.ReadParseDictionary;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HangmanEvil extends Hangman {

    /**
     * stores a list of words with same word length or same family grouping
     */
    private List<String> sameWordFamily;

    /**
     * partition key of selected word family from most recent guess
     */
    private List<String> lastPartitionKey;

    /**
     * partitioned words into families based on user guess
     */
    private HashMap <List<String>,List<String>> listOfWordFamilies;


    /**
     * creates new instance of evil Hangman game with input dictionary
     */
    public HangmanEvil(ReadParseDictionary dictionary){
        super(dictionary);
        pickInitialWord();
        initializeSameWordFamily();
    }

    /**
     * set initial sameWordFamily variable
     */
    public void initializeSameWordFamily(){
        List<String> cleanWordList = getDictionary().getCleanWordList();
        int gameWordLength = getGameWord().length(); // store length of initial game word
        List<String> sameWordFamily = new ArrayList<>(); // to store first same word family

        // finds other words with same length as initial game word
        for(String word: cleanWordList){
            if(word.length() == gameWordLength){
                sameWordFamily.add(word);
            }
        }

        List<String> lastPartitionKey = new ArrayList<>();
        for (int i=0; i<gameWordLength;i++){
            lastPartitionKey.add("*");
        }
        this.lastPartitionKey = lastPartitionKey;


        this.sameWordFamily = sameWordFamily;
    }

    /**
     * update the game word based on max word family
     */
    public void updateGameWord(){
        wordPartition();
        int maxSize = -1; // stores max size out of word families
        List<String> maxWordFamily = new ArrayList<>(); // store max wordFamily
        List<String> maxPartitionKey= new ArrayList<>();

        for (List<String> key : listOfWordFamilies.keySet()) {

            List<String> value = listOfWordFamilies.get(key);

            // update max size, word family and partition key if larger value found
            if(value.size()>maxSize){
                maxSize=value.size();
                maxWordFamily = value;
                maxPartitionKey = key;
            }
        }
        // at this point, max word family has been found
        this.sameWordFamily= maxWordFamily;
        this.lastPartitionKey=maxPartitionKey;


        //set a random word from sameWordFamily as the game word
        String randWord = generateRandomWord(sameWordFamily);
        setGameWord(randWord);

    }

    /**
     * update correct letters using the last partition key
     */
    @Override
    public void updateCorrectLetters() {
        updateGameWord();
        String[] correctLetters = new String[getGameWord().length()];
        for(int i=0; i<lastPartitionKey.size(); i++){
            correctLetters[i] = lastPartitionKey.get(i);
        }
        setCorrectLetters(correctLetters);
    }

    /**
     * partition words into word families based on user's current and past guesses
     */
    public void wordPartition(){
        // get most recent letter guess
        HashMap <List<String>,List<String>> listOfWordFamilies = new HashMap <List<String>,List<String>>();
        String[] correctLetters = getCorrectLetters();

        // iterate through each word in this family
        for(String word: sameWordFamily){
            List<String> partitionKey = createPartitionKey(word);
            // partition key has been created

            // skip words that don't contain the most recently guessed letter.
            // those words shouldn't be in the list of word families
            if(!partitionKey.contains(getMostRecentGuess())){
                continue;
            }

            // skip words that contain a prior incorrect guess
            boolean containsPriorIncorrectGuess=false;
            for(String letter: getIncorrectLetters()) {
                if (word.contains(letter)) containsPriorIncorrectGuess=true;
            }
            if(containsPriorIncorrectGuess) continue;

            // update the family if its already in the list of families
            if(listOfWordFamilies.containsKey(partitionKey)){
                List<String> thisFamily = listOfWordFamilies.get(partitionKey);
                thisFamily.add(word);
                listOfWordFamilies.put(partitionKey,thisFamily);
            }// create the family if it isn't in the list of families
            else{
                List<String> newFamily = new ArrayList<>();
                newFamily.add(word);
                listOfWordFamilies.put(partitionKey,newFamily);
            }
        }
        this.listOfWordFamilies=listOfWordFamilies;
    }

    /**
     * creates partition key. E.g. heel will have the key h*** if h is guessed correctly
     * @param word to create a partition key for
     * @return partition key as a list of string letters
     */
    public List<String> createPartitionKey(String word){
        // get most recent letter guess
        List<String> partitionKey = new ArrayList<>();

        // i dont need to know others. because captured by same family
        // find out positioning of new guess
        for(int i=0; i<word.length();i++){
            String thisLetter = word.charAt(i) +"";

            // build the partition key using prev correctly guessed letters and the newly guessed letter
            if(thisLetter.equals(getMostRecentGuess())){
                partitionKey.add(getMostRecentGuess());
            }
            else if(containsCorrectLetter(thisLetter)){
                partitionKey.add(thisLetter);
            }
            // use "*" for unguessed letters in partition key
            else{
                partitionKey.add("*");
            }
        }
        return partitionKey;
    }

    /**
     * set same word family
     * @param sameWordFamily to set variable
     */
    public void setSameWordFamily(List<String> sameWordFamily) {
        this.sameWordFamily = sameWordFamily;
    }

    /**
     * set partition key
     * @param lastPartitionKey to set variable
     */
    public void setLastPartitionKey(List<String> lastPartitionKey) {
        this.lastPartitionKey = lastPartitionKey;
    }

    /**
     * set list of word families
     * @param listOfWordFamilies to set variable
     */
    public void setListOfWordFamilies(HashMap<List<String>, List<String>> listOfWordFamilies) {
        this.listOfWordFamilies = listOfWordFamilies;
    }

    /**
     * get same word family variable
     * @return same word family
     */
    public List<String> getSameWordFamily() {
        return sameWordFamily;
    }

    /**
     * get list of word families
     * @return variable
     */
    public HashMap<List<String>, List<String>> getListOfWordFamilies() {
        return listOfWordFamilies;
    }

    /**
     * get partition key
     * @return partition key
     */
    public List<String> getLastPartitionKey() {
        return lastPartitionKey;
    }
}
