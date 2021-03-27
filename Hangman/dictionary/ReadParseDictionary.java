package com.company.Hangman.dictionary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadParseDictionary {

    /**
     * contains all words in the input text
     */
    private List<String> allWordList;

    /**
     * stores only clean words from input list
     */
    private List<String> cleanWordList;

    /**
     * read all lines into a list of all words
     * and stores it in the allWordList variable
     * @param fileName for name of the input file
     * @throws Exception if file can't be read
     */
    public void readAllWords(String fileName) throws Exception {
        List<String> allWordList; // stores all words in a list
        File myFile = new File(fileName);

        // try to open file. raise exception if file can't be read
        try {
            allWordList = Files.readAllLines(Paths.get(myFile.getPath()));
        } catch (IOException e) {
            throw new Exception("File can't be found or read");
        }

        this.allWordList = allWordList;
    }

    /**
     * extracts clean words from all word list.
     * stores this in cleanWordList variable
     */
    public void readCleanWords(){

        // store clean word list
        List<String> cleanWordList = new ArrayList<>();

        // iterate through all words in the list
        for(int i=0; i<allWordList.size();i++){

            // stores this line without spaces at beg or end
            String line = allWordList.get(i).strip();

            Boolean cleanWord = true; // tracks whether a word is a clean word

            // iterate through each character in that line in the word list
            for(int j=0; j<line.length();j++){
                Character character = line.charAt(j);

                // checks if character is upper case
                if(Character.isUpperCase(character)){
                    cleanWord= false;
                    break;
                }

                // check abbreviations, apostrophe, hyphen and compound words
                if(line.contains(".") || line.contains("'") || line.contains("-") || line.contains(" ")){
                    cleanWord= false;
                    break;
                }

                // check contains a digit
                if(Character.isDigit(character)){
                    cleanWord= false;
                    break;
                }

            }
            // if this is a valid word, add to clean word list
            if(cleanWord){
                cleanWordList.add(line.strip());
            }
        }

        this.cleanWordList = cleanWordList;
    }

    /**
     *
     * @return allWordList variable
     */
    public List<String> getAllWordList() {
        return allWordList;
    }

    /**
     *
     * @return cleanWordList variable
     */
    public List<String> getCleanWordList() {
        return cleanWordList;
    }

    /**
     * sets allWordList variable
     * @param allWordList as input
     */
    public void setAllWordList(List<String> allWordList) {
        this.allWordList = allWordList;
    }

    /**
     * sets clean Word List
     * @param cleanWordList as variable
     */
    public void setCleanWordList(List<String> cleanWordList) {
        this.cleanWordList = cleanWordList;
    }
}
