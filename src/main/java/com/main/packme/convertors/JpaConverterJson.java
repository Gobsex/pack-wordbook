package com.main.packme.convertors;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.packme.dao.components.WordList;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<WordList, String> {

    private final static Gson gson = new GsonBuilder().create();

    @Override
    public String convertToDatabaseColumn(WordList meta) {
            return gson.toJson(meta);
    }

    @Override
    public WordList convertToEntityAttribute(String dbData) {
        return gson.fromJson(dbData,WordList.class);
    }

}
