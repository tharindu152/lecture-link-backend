package lk.ac.iit.lecture_link.converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private String status;

    Status(String type) {
        this.status = type;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
