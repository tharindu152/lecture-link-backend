package lk.ac.iit.lecture_link.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TimePreference {
    WEEKEND("WEEKEND"), WEEKDAY("WEEKDAY"), FLEXIBLE("FLEXIBLE");

    private String timePreference;

    TimePreference(String type) {
        this.timePreference = type;
    }

    @JsonValue
    public String getTimePreference() {
        return timePreference;
    }
}
