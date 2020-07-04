package com.main.packme.dao.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.concurrent.ThreadLocalRandom;

public class Word{
    private String key;
    private String value;

    public Word() {
    }

    public Word(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

