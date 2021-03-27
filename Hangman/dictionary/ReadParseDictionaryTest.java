package com.company.Hangman.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadParseDictionaryTest {

    /**
     * test readwords method
     * @throws Exception if file cant be read
     */
    @Test
    void readWords() throws Exception {
        ReadParseDictionary fileReaderWriter = new ReadParseDictionary();
        fileReaderWriter.readAllWords("C:\\Users\\chuka\\IdeaProjects\\Projects 591 Java\\src\\com\\company\\Hangman\\dictionary\\words.txt");
        assertEquals("2",fileReaderWriter.getAllWordList().get(0));
        assertEquals("ZZZ",fileReaderWriter.getAllWordList().get(466458));
    }

    /**
     * test clean word list method
     * @throws Exception if file can't be read
     */
    @Test
    void getCleanWordList() throws Exception {
        ReadParseDictionary fileReaderWriter = new ReadParseDictionary();
        fileReaderWriter.readAllWords("C:\\Users\\chuka\\IdeaProjects\\Projects 591 Java\\src\\com\\company\\Hangman\\dictionary\\words_test.txt");
        fileReaderWriter.readCleanWords();

        // doesn't read words with uppercase?
        assertEquals("osakwe",fileReaderWriter.getCleanWordList().get(0));

        // doesn't read words with abbreviaions?
        assertEquals("osakwetwo",fileReaderWriter.getCleanWordList().get(1));

        // doesn't read words with apostrophe?
        assertEquals("osakwethree",fileReaderWriter.getCleanWordList().get(2));

        // doesn't read words with hyphen?
        assertEquals("osakwefour",fileReaderWriter.getCleanWordList().get(3));

        // doesn't read compound words?
        assertEquals("osakwefive",fileReaderWriter.getCleanWordList().get(4));

        // doesn't read words with digits?
        assertEquals("osakwesix",fileReaderWriter.getCleanWordList().get(5));

        assertEquals(6,fileReaderWriter.getCleanWordList().size());
    }
}