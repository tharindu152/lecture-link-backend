package lk.ac.iit.lecture_link.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {
    DOCTORATE("Doctorate"), MASTERS("Masters"), POSTGRADUATE("PostGraduate"), BACHELORS("Bachelors"), HND("HND"), HNC("HNC");

    private String level;

    Level(String type) {
        this.level = type;
    }

    @JsonValue
    public String getLevel() {
        return level;
    }
}
