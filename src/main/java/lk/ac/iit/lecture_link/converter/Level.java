package lk.ac.iit.lecture_link.converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {
    MSC("MSc"), BSC("BSc"), HND("hnd"), PGD("pgd");

    private String level;

    Level(String type) {
        this.level = type;
    }

    @JsonValue
    public String getLevel() {
        return level;
    }
}
