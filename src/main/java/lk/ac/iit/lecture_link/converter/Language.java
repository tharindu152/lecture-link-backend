package lk.ac.iit.lecture_link.converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Language {
    ENGLISH("ENGLISH"), SINHALA("SINHALA"), TAMIL("TAMIL");

    private String language;

    Language(String type) {
        this.language = type;
    }

    @JsonValue
    public String getLanguage() {
        return language;
    }
}
