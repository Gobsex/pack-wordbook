package com.main.packme.dao.components;

import java.util.ArrayList;
import java.util.List;

public class WordList {

    private List<Word> words = new ArrayList<>();

    public WordList(List<Word> words) {
        this.words = words;
    }

    public WordList() {
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public boolean isNull(){
        if(words.isEmpty()){
            return true;
        }
        return false;
    }

    public void add(Word word) {
        words.add(word);
    }
    public void remove(long id) {
        words.remove((int)id);
    }

}
