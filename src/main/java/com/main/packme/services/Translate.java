package com.main.packme.services;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Component;

@Component
public class Translate
{
    private com.google.cloud.translate.Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyDmxNAr2Jt_pAYKmeKE7Xu3id3SHJyw-UU").build().getService();
    public String getTranslation(String text,String sourceLanguage,String targetLanguage) {

        if(sourceLanguage.equals(targetLanguage)){

            return text;
        }
        Translation translation = translate.translate(text, com.google.cloud.translate.Translate.TranslateOption.sourceLanguage(sourceLanguage), com.google.cloud.translate.Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }
    public String getLanguage(String text) {
        Detection detect = translate.detect(text);
        return detect.getLanguage();
    }
}
