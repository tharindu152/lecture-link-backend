package lk.ac.iit.lecture_link.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {
    DOCTORATE("DOCTORATE"), MASTERS("MASTERS"), POSTGRADUATE("POSTGRADUATE"), BACHELORS("BACHELORS"), HND("HND"), HNC("HNC");

    private String level;

    Level(String type) {
        this.level = type;
    }

    @JsonValue
    public String getLevel() {
        return level;
    }
}
