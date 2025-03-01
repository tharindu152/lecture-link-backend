package lk.ac.iit.lecture_link.converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    LECTURER("LECTURER"), INSTITUTE("INSTITUTE");

    private String role;

    Role(String type) {
        this.role = type;
    }

    @JsonValue
    public String getRole() {
        return role;
    }
}
