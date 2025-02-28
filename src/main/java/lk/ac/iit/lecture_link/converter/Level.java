package lk.ac.iit.lecture_link.converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {
    PHD("PHD"), MSC("MSC"), BSC("BSC"), HND("HND"), PGD("PGD");

    private String level;

    Level(String type) {
        this.level = type;
    }

    @JsonValue
    public String getLevel() {
        return level;
    }
}
