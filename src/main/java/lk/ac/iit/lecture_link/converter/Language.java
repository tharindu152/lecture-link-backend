package lk.ac.iit.lecture_link.converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Language {
    ENGLISH("english"), SINHALA("sinhala"), TAMIL("tamil");

    private String language;

    Language(String type) {
        this.language = type;
    }

    @JsonValue
    public String getLanguage() {
        return language;
    }
}
