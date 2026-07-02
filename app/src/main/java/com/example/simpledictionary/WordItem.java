package com.example.simpledictionary;

import java.io.Serializable;

public class WordItem implements Serializable {
    private final String word;
    private final String meaning;

    public WordItem(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public String getWord() { return word; }

    public String getMeaning() { return meaning; }
}