package lk.ac.iit.lecture_link.enums;

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
